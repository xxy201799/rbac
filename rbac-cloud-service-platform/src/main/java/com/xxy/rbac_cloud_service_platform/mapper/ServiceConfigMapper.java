package com.xxy.rbac_cloud_service_platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxy.rbac_cloud_service_platform.entity.ServiceConfig;

/**
 * @Author: XXY
 * @Date: 2020/5/4 17:06
 */
public interface ServiceConfigMapper extends BaseMapper<ServiceConfig> {
    ServiceConfig queryServiceConfigByState(Integer state);
}
