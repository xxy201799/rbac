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

import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.RuleEntity;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.SystemRuleEntity;
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
@TableName("service_system_rule")
public class ServiceSystemRule   extends Model<ServiceSystemRule> implements Serializable {
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
    @Column(name = "highest_system_load")
    private Double highestSystemLoad;
    @Column(name = "avg_rt")
    private Long avgRt;
    @Column(name = "max_thread")
    private Long maxThread;
    @Column(name = "qps")
    private Double qps;
    @Column(name = "highest_cpu_usage")
    private Double highestCpuUsage;
    @Column(name = "gmt_create")
    private Date gmtCreate;
    @Column(name = "gmt_modified")
    private Date gmtModified;


}
