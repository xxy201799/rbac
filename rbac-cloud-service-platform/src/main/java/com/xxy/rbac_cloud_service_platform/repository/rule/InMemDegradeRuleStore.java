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

import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.DegradeRuleEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leyou
 */
@Component
public class InMemDegradeRuleStore extends RdbcRuleRepositoryAdapter<DegradeRuleEntity> {

    private static AtomicLong ids = new AtomicLong(0);
    @PersistenceContext
    private EntityManager em;

    @Override
    protected void putRule(DegradeRuleEntity processedEntity) {

    }

    @Override
    protected long nextId() {
        StringBuilder builder=new StringBuilder();
        builder.append("select id from service_degrade_rule order by id DESC limit 1");
        Query query= em.createQuery(builder.toString());
        Integer id= (Integer) query.getSingleResult();
        return ++id;
    }
}
