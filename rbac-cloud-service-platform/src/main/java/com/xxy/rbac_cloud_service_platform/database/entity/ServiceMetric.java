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

/**
 * @Author: XXY
 * @Date: 2020/5/10 16:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TableName("service_metric")
public class ServiceMetric extends Model<ServiceMetric> implements Serializable {
    private static final long serialVersionUID = 7200023615444172715L;

    /**id，主键*/
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**创建时间*/
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**修改时间*/
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**应用名称*/
    @Column(name = "app")
    private String app;

    /**统计时间*/
    @Column(name = "timestamp")
    private Date timestamp;

    /**资源名称*/
    @Column(name = "resource")
    private String resource;

    /**通过qps*/
    @Column(name = "pass_qps")
    private Long passQps;

    /**成功qps*/
    @Column(name = "success_qps")
    private Long successQps;

    /**限流qps*/
    @Column(name = "block_qps")
    private Long blockQps;

    /**发送异常的次数*/
    @Column(name = "exception_qps")
    private Long exceptionQps;

    /**耗时时间(ms)*/
    @Column(name = "rt")
    private Double rt;

    /**本次聚合的总条数*/
    @Column(name = "_count")
    private Integer count;

    /**资源的hashCode*/
    @Column(name = "resource_code")
    private Integer resourceCode;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}
