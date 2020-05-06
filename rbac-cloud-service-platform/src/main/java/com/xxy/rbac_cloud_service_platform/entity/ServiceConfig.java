package com.xxy.rbac_cloud_service_platform.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xxy.common.core.constants.enums.RegisterTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: XXY
 * @Date: 2020/5/3 12:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceConfig extends Model<ServiceConfig> {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private RegisterTypeEnum typeEnum;
    private String registerAddress;
    private Integer clusterModeFlag;
    private String userName;
    private Long userId;
    private Integer state;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String configAddress;
    private String projectName;
    private String password;
}
