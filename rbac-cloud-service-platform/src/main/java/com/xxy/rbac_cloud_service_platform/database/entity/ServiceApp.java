package com.xxy.rbac_cloud_service_platform.database.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TableName("service_app")
public class ServiceApp extends Model<ServiceApp> implements Serializable {
    private static final long serialVersionUID = 8600023435444172715L;

    /**
     * id，主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    /**
     * 应用
     */
    @Column(name = "app")
    private String app;
    /**
     * 应用类型
     */
    @Column(name = "appType")
    private Integer appType;


    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;
    /**
     * 活动配置
     */
    @Column(name = "active_console")
    private String activeConsole;
    /**
     * 上次拉取时间
     */
    @Column(name = "last_fetch")
    private Date lastFetch;
}
