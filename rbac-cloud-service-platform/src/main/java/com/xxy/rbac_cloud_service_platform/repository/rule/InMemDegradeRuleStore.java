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

import com.xxy.rbac_cloud_service_platform.database.entity.rule.ServiceDegradeRule;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.DegradeRuleEntity;
import org.springframework.stereotype.Component;

/**
 * @author leyou
 */
@Component
public class InMemDegradeRuleStore extends RdbcRuleRepositoryAdapter<DegradeRuleEntity,ServiceDegradeRule> {

    String Entity_NAME ="ServiceDegradeRule";
    String Table_NAME ="service_degrade_rule";


    @Override
    protected ServiceDegradeRule getModel(Long id) {
        ServiceDegradeRule serviceDegradeRule = new ServiceDegradeRule();
        serviceDegradeRule.setId(id);
        return serviceDegradeRule;
    }

    @Override
    protected ServiceDegradeRule getEmptyModel() {
        return  new ServiceDegradeRule();
    }

    @Override
    public String getTableName() {
        return this.Table_NAME;
    }

    @Override
    protected DegradeRuleEntity getEntity() {
        return new DegradeRuleEntity();
    }

    @Override
    public String getEntityName() {
        return Entity_NAME;
    }


}
