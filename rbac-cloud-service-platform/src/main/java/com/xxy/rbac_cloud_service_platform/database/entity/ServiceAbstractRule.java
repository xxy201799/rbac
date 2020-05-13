package com.xxy.rbac_cloud_service_platform.database.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: XXY
 * @Date: 2020/5/12 23:44
 */
@Data
public abstract class ServiceAbstractRule {
    protected Long id;

    protected String app;
    protected String ip;
    protected Integer port;

    private Date gmtCreate;
    private Date gmtModified;
}
