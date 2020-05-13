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
@TableName("service_MachineInfo")
public class ServiceMachineInfo extends Model<ServiceMachineInfo> implements Serializable {
    private static final long serialVersionUID = 7200023435444172715L;
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
    @Column(name = "app_type")
    private Integer appType;
    /**
     * 主机名
     */
    @Column(name = "host_name")
    private String hostName;
    /**
     * ip
     */
    @Column(name = "ip")
    private String ip;
    /**
     * 端口
     */
    @Column(name = "port")
    private Integer port;
    /**
     * 上次心跳时间
     */
    @Column(name = "last_heartbeat")
    private long lastHeartbeat;
    /**
     * 心跳版本
     */
    @Column(name = "heartbeat_version")
    private long heartbeatVersion;
    /**
     * 维护时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;
    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
