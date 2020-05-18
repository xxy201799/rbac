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
package com.xxy.rbac_cloud_service_platform.repository.rule;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xxy.common.core.util.SpringUtil;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMachineInfo;
import com.xxy.rbac_cloud_service_platform.database.entity.rule.ServiceAuthorityRule;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.AuthorityRuleEntity;
import com.xxy.rbac_cloud_service_platform.discovery.MachineInfo;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory storage for authority rules.
 *
 * @author Eric Zhao
 * @since 0.2.1
 */
@Component
public class InMemAuthorityRuleStore extends RdbcRuleRepositoryAdapter<AuthorityRuleEntity,ServiceAuthorityRule> {

    private static AtomicLong ids = new AtomicLong(0);
    @PersistenceContext
    private EntityManager em;
    String Table_NAME="ServiceAuthorityRule";
    @Override
    public AuthorityRuleEntity save(AuthorityRuleEntity entity) {
        return super.save(entity);
    }


    @Override
    protected ServiceAuthorityRule getModel(Long id) {
        ServiceAuthorityRule serviceAuthorityRule = new ServiceAuthorityRule();
        serviceAuthorityRule.setId(id);
        return serviceAuthorityRule;
    }

    @Override
    protected ServiceAuthorityRule getEmptyModel() {
        return  new ServiceAuthorityRule();
    }

    @Override
    protected String getTableName() {
        return this.Table_NAME;
    }

    @Override
    protected AuthorityRuleEntity getEntity() {
        return new AuthorityRuleEntity();
    }


}
