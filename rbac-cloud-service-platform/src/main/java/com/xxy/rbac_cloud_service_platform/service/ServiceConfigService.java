package com.xxy.rbac_cloud_service_platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxy.rbac_cloud_service_platform.entity.ServiceConfig;

/**
 * @Author: XXY
 * @Date: 2020/5/4 20:13
 */
public interface ServiceConfigService extends IService<ServiceConfig>{
    void openServicePlatForm(Long id);
    void releaseServicePlatForm(Long id);

}
