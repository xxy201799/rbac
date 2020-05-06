package com.xxy.rbac_cloud_service_platform.service;

import com.xxy.rbac_cloud_service_platform.config.RegisterCenterBeanFactory;
import com.xxy.rbac_cloud_service_platform.register.RegisterOperationConfiguration;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.registry.Registry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: XXY
 * @Date: 2020/5/5 22:33
 */
public abstract class AbstractService {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
    protected Registry registry;

    protected RegisterOperationConfiguration dynamicConfiguration;

}
