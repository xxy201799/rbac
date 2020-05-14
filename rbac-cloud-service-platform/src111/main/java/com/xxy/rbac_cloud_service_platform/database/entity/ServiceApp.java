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
}
