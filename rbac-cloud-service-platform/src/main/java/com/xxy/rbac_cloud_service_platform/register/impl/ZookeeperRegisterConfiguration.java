package com.xxy.rbac_cloud_service_platform.register.impl;

import com.xxy.common.core.constants.RPCConstants;
import com.xxy.rbac_cloud_service_platform.register.RegisterOperationConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @Author: XXY
 * @Date: 2020/5/3 0:51
 */
@Service("zookeeper")
public class ZookeeperRegisterConfiguration implements RegisterOperationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegisterConfiguration.class);
    private CuratorFramework zkClient;
    private URL url;
    private String root;

    @Override
    public void setUrl(URL url) {
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void init() {
        if (url == null) {
            throw new IllegalStateException("server url is null, cannot init");
        }
        CuratorFrameworkFactory.Builder zkClientBuilder = CuratorFrameworkFactory.builder().
                connectString(url.getAddress()).
                retryPolicy(new ExponentialBackoffRetry(1000, 3));
        if (StringUtils.isNotEmpty(url.getUsername()) && StringUtils.isNotEmpty(url.getPassword())) {
            // add authorization
            String auth = url.getUsername() + ":" + url.getPassword();
            zkClientBuilder.authorization("digest", auth.getBytes());
        }
        zkClient = zkClientBuilder.build();
        String group = url.getParameter(RPCConstants.GROUP_KEY, RPCConstants.DEFAULT_ROOT);
        if (!group.startsWith(RPCConstants.PATH_SEPARATOR)) {
            group = RPCConstants.PATH_SEPARATOR + group;
        }
        root = group;
        zkClient.start();
    }



    @Override
    public String setConfig(String key, String value) {
        return setConfig(null, key, value);
    }

    @Override
    public String getConfig(String key) {
        return getConfig(null, key);
    }

    @Override
    public boolean deleteConfig(String key) {
        return deleteConfig(null, key);
    }

    @Override
    public String setConfig(String group, String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value cannot be null");
        }
        String path = getNodePath(key, group);
        try {
            if (zkClient.checkExists().forPath(path) == null) {
                zkClient.create().creatingParentsIfNeeded().forPath(path);
            }
            zkClient.setData().forPath(path, value.getBytes());
            return value;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getConfig(String group, String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        String path = getNodePath(key, group);

        try {
            if (zkClient.checkExists().forPath(path) == null) {
                return null;
            }
            return new String(zkClient.getData().forPath(path));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean deleteConfig(String group, String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        String path = getNodePath(key, group);
        try {
            zkClient.delete().forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public String getPath(String key) {
        return getNodePath(key, null);
    }

    @Override
    public String getPath(String group, String key) {
        return getNodePath(key, group);
    }

    @Override
    public void destory() {
        zkClient.close();
    }

    private String getNodePath(String path, String group) {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        return toRootDir(group) + path;
    }

    private String toRootDir(String group) {
        if (group != null) {
            if (!group.startsWith(RPCConstants.PATH_SEPARATOR)) {
                root = RPCConstants.PATH_SEPARATOR + group;
            } else {
                root = group;
            }
        }
        if (root.equals(RPCConstants.PATH_SEPARATOR)) {
            return root;
        }
        return root + RPCConstants.PATH_SEPARATOR;
    }
}
