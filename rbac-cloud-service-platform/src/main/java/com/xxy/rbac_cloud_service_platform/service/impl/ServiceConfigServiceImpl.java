package com.xxy.rbac_cloud_service_platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxy.common.core.constants.RPCConstants;
import com.xxy.common.core.exception.RbacBizException;
import com.xxy.rbac_cloud_service_platform.config.RegisterCenterBeanFactory;
import com.xxy.rbac_cloud_service_platform.entity.ServiceConfig;
import com.xxy.rbac_cloud_service_platform.mapper.ServiceConfigMapper;
import com.xxy.rbac_cloud_service_platform.register.RegisterOperationConfiguration;
import com.xxy.rbac_cloud_service_platform.service.ServiceConfigService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @Author: XXY
 * @Date: 2020/5/4 20:19
 */
@Service
@AllArgsConstructor
public class ServiceConfigServiceImpl extends ServiceImpl<ServiceConfigMapper, ServiceConfig> implements ServiceConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceConfigServiceImpl.class);
    private final ServiceConfigMapper serviceConfigMapper;
    private final RegisterOperationConfiguration dynamicConfiguration;

    @Override
    public void openServicePlatForm(Long id) {
        ServiceConfig serviceConfig = serviceConfigMapper.queryServiceConfigByState(1);
        if(serviceConfig != null){
            logger.error("you had init a system, don't need init again");
            throw new RbacBizException("you had init a system, don't need init again");
        }
        ServiceConfig queryOne = serviceConfigMapper.selectById(id);
        if(queryOne == null){
            logger.error("the record what you want to query not exist, id:" + id);
            throw new RbacBizException("the record what you want to query not exist, id:" + id);
        }

        serviceConfig.setState(1);
        baseMapper.updateById(serviceConfig);
    }

    @Override
    public void releaseServicePlatForm(Long id) {
        ServiceConfig serviceConfig = serviceConfigMapper.queryServiceConfigByState(1);
        if(serviceConfig == null){
            throw new RbacBizException("you had not init a system, please init a record");
        }
        ServiceConfig queryOne = serviceConfigMapper.selectById(id);
        if(queryOne == null){
            throw new RbacBizException("the record what you want to query not exist, id:" + id);
        }
        RegisterCenterBeanFactory.removeConfiguration(queryOne.getProjectName());
        serviceConfig.setState(0);
        baseMapper.updateById(serviceConfig);
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
