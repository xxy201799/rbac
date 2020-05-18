/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xxy.rbac_cloud_service_platform.database.entity.rule;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.DegradeRuleEntity;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.RuleEntity;
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
@TableName("service_degrade_rule")
public class ServiceDegradeRule  extends Model<ServiceDegradeRule> implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "app")
    private String app;
    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private Integer port;
    @Column(name = "resource")
    private String resource;
    @Column(name = "limit_app")
    private String limitApp;
    @Column(name = "count")
    private Double count;
    @Column(name = "time_window")
    private Integer timeWindow;
    /**
     * 0 rt 限流; 1为异常;
     */
    @Column(name = "grade")
    private Integer grade;
    @Column(name = "gmt_create")
    private Date gmtCreate;
    @Column(name = "gmt_modified")
    private Date gmtModified;





    }

