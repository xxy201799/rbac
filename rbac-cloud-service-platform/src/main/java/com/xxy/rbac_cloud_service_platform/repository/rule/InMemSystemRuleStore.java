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

import com.xxy.rbac_cloud_service_platform.database.entity.rule.ServiceSystemRule;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.SystemRuleEntity;
import org.springframework.stereotype.Component;

/**
 * @author leyou
 */
@Component
public class InMemSystemRuleStore extends RdbcRuleRepositoryAdapter<SystemRuleEntity, ServiceSystemRule> {

    String Entity_NAME ="ServiceSystemRule";
    String Table_NAME ="service_system_rule";
    @Override
    protected void putRule(SystemRuleEntity processedEntity) {

    }

    @Override
    protected ServiceSystemRule getEmptyModel() {
        return  new ServiceSystemRule();
    }




    @Override
    public String getTableName() {
        return this.Table_NAME;
    }

    @Override
    protected SystemRuleEntity getEntity() {
        return new SystemRuleEntity();
    }

    @Override
    public String getEntityName() {
        return Entity_NAME;
    }


}
