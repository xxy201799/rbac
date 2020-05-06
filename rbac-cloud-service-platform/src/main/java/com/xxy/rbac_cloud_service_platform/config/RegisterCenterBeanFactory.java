package com.xxy.rbac_cloud_service_platform.config;

import com.xxy.common.core.constants.RPCConstants;
import com.xxy.common.core.exception.RbacBizException;
import com.xxy.rbac_cloud_service_platform.register.RegisterOperationConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: XXY
 * @Date: 2020/5/5 20:21
 */
public class RegisterCenterBeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(RegisterCenterBeanFactory.class);
    public static Map<String, RegisterOperationConfiguration> map = new ConcurrentHashMap<>();
    public static Map<String, Registry> registryMap = new ConcurrentHashMap<String, Registry>();
    public static Map<String,URL> registryUrlMap = new ConcurrentHashMap<>();
    public static Map<String,URL> metadataUrlMap = new ConcurrentHashMap<>();
    public static Map<String,URL> configCenterUrlMap = new ConcurrentHashMap<>();

    public static RegisterOperationConfiguration getDynamicConfiguration(String configCenter, String registryAddress, String username, String password, String projectName) {
        RegisterOperationConfiguration dynamicConfiguration = map.get(projectName);
        if(dynamicConfiguration != null){
            return dynamicConfiguration;
        }
        if (StringUtils.isNotEmpty(configCenter)) {
            URL configCenterUrl = compareAndSetUrl(configCenter, username, password, projectName, configCenterUrlMap);
            dynamicConfiguration = ExtensionLoader.getExtensionLoader(RegisterOperationConfiguration.class).getExtension(configCenterUrl.getProtocol());
            dynamicConfiguration.setUrl(configCenterUrl);
            dynamicConfiguration.init();
            String config = dynamicConfiguration.getConfig(RPCConstants.GLOBAL_CONFIG_PATH);

            if (StringUtils.isNotEmpty(config)) {
                Arrays.stream(config.split("\n")).forEach(s -> {
                    if(s.startsWith(RPCConstants.REGISTRY_ADDRESS)) {
                        String registryIp = s.split("=")[1].trim();
                        compareAndSetUrl(registryIp, username, password, projectName, registryUrlMap);
                    } else if (s.startsWith(RPCConstants.METADATA_ADDRESS)) {
                        compareAndSetUrl(s.split("=")[1].trim(), username, password, projectName, metadataUrlMap);
                    }
                });
            }
        }
        if (dynamicConfiguration == null) {
            if (StringUtils.isNotEmpty(registryAddress)) {
                URL registryUrl = compareAndSetUrl(registryAddress, username, password, projectName, registryUrlMap);
                dynamicConfiguration = ExtensionLoader.getExtensionLoader(RegisterOperationConfiguration.class).getExtension(registryUrl.getProtocol());
                dynamicConfiguration.setUrl(registryUrl);
                dynamicConfiguration.init();
                logger.warn("you are using dubbo.registry.address");
            } else {
                throw new RbacBizException("Either config center or registry address is needed");
                //throw exception
            }
        }
        map.put(projectName, dynamicConfiguration);
        return dynamicConfiguration;
    }
    public static void removeConfiguration(String projectName){
        RegisterOperationConfiguration configuration = map.get(projectName);
        configuration.destory();
        map.remove(projectName);
    }


    public static Registry getRegistry(String registryAddress, String username, String password, String projectName) {
        Registry registry = registryMap.get(projectName);
        if (registry != null){
            return registry;
        }
        URL registryUrl = registryUrlMap.get(projectName);
        if (registryUrl == null) {
            if (StringUtils.isBlank(registryAddress)) {
                throw new RbacBizException("Either config center or registry address is needed");
            }
            registryUrl = compareAndSetUrl(registryAddress, username, password, projectName, registryUrlMap);
        }
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        registry = registryFactory.getRegistry(registryUrl);
        registryMap.put(projectName, registry);
        return registry;
    }

    private static URL compareAndSetUrl(String address, String username, String password, String projectName, Map<String, URL> urlMap){
        URL url = urlMap.get(projectName);
        if(url == null){
            url = formUrl(address, RPCConstants.CONFIG_CENTER_GROUP, username, password);
            urlMap.put(projectName, url);
        }
        return url;
    }

    private static URL formUrl(String config, String group, String username, String password) {
        URL url = URL.valueOf(config);
        if (StringUtils.isNotEmpty(group)) {
            url = url.addParameter(RPCConstants.GROUP_KEY, group);
        }
        if (StringUtils.isNotEmpty(username)) {
            url = url.setUsername(username);
        }
        if (StringUtils.isNotEmpty(password)) {
            url = url.setPassword(password);
        }
        return url;
    }




}
