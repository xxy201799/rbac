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

import com.xxy.rbac_cloud_service_platform.database.entity.rule.ServiceAuthorityRule;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.AuthorityRuleEntity;
import org.springframework.stereotype.Component;

/**
 * In-memory storage for authority rules.
 *
 * @author Eric Zhao
 * @since 0.2.1
 */
@Component
public class InMemAuthorityRuleStore extends RdbcRuleRepositoryAdapter<AuthorityRuleEntity,ServiceAuthorityRule> {

    String Entity_NAME ="ServiceAuthorityRule";
    String table_NAME ="service_authority_rule";



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
    public String getTableName() {
        return this.table_NAME;
    }

    @Override
    protected AuthorityRuleEntity getEntity() {
        return new AuthorityRuleEntity();
    }

    @Override
    public String getEntityName() {
        return this.Entity_NAME;
    }


}
