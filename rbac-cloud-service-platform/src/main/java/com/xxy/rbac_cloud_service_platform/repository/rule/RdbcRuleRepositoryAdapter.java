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

import com.alibaba.csp.sentinel.util.AssertUtil;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.base.CaseFormat;
import com.xxy.common.core.util.SpringUtil;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.RuleEntity;
import com.xxy.rbac_cloud_service_platform.discovery.MachineInfo;
import net.bytebuddy.asm.Advice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;

/**
 * @author leyou
 */
public abstract class RdbcRuleRepositoryAdapter<T extends RuleEntity, M extends Model> implements RuleRepository<T, Long> {
    @PersistenceContext
    private EntityManager em;



    @Override
    @Transactional
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(nextId());
        }
        T processedEntity = preProcess(entity);
        if (processedEntity != null) {
            putRule(processedEntity);
        }
        return processedEntity;
    }

    protected void putRule(T processedEntity) {
        M model = getEmptyModel();
       processedEntity.setId(null);
        SpringUtil.copyPropertiesIgnoreNull(processedEntity, model);
        em.persist(model);
    }



    protected  M getModel(Long id){
        StringBuilder hql=new StringBuilder();
        hql.append("from "+getEntityName());
        hql.append(" where id=:id");
        Query query=em.createQuery(hql.toString());
        query.setParameter("id", id);
        try {
            M model=(M)query.getSingleResult();
            return model;
        }
        catch (NoResultException e){
            return  getEmptyModel();
        }

    };
    protected abstract   M getEmptyModel();
    ;

    protected void clear() {
        String table = getEntityName();
        Query query = em.createNativeQuery("truncate table "+table);
        return;
    }

    ;

    protected T remove(Long id) {
        T entity = getEntity(id);
        M model = getModel(id);
      em.remove(model);
        return entity;
    }

    ;

    protected T getEntity(Long id) {
        M model = getModel(id);
       T entity=  getEntity();
       SpringUtil.copyPropertiesIgnoreNull(model,entity);
        return entity;
    }

    public  abstract   String getTableName(
    );






    private Map<Long, T> getRealEntitys(List<M> authorityRules) {
        Map<Long, T> realRules = new ConcurrentHashMap<>(16);
        for (M authorityRule : authorityRules) {
            T entity = getEntity();
            SpringUtil.copyPropertiesIgnoreNull(authorityRule, entity);
            realRules.put(entity.getId(), entity);
        }
        return realRules;
    }

    protected abstract T getEntity();
    protected Map<Long, T> getByMachine(MachineInfo machineInfo) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from "+getEntityName());
        hql.append(" WHERE app=:app");
        hql.append(" AND ip=:ip ");
        hql.append(" AND port=:port ");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", machineInfo.getApp());
        query.setParameter("ip", machineInfo.getIp());
        query.setParameter("port", machineInfo.getPort());
        List<M> rules = query.getResultList();
        return getRealEntitys(rules);
    }

    protected Map<Long, T> getByApp(String appName) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from " + getEntityName());
        hql.append(" WHERE app=:appName");
        Query query = em.createQuery(hql.toString());
        query.setParameter("appName", appName);
        List<M> models = query.getResultList();
        return getRealEntitys(models);
    }

    ;

    @Override
    public List<T> saveAll(List<T> rules) {
        // TODO: check here.
        clearAll();
        if (rules == null) {
            return null;
        }
        List<T> savedRules = new ArrayList<>(rules.size());
        for (T rule : rules) {
            savedRules.add(save(rule));
        }
        return savedRules;
    }

    @Override
    @Transactional
    public T delete(Long id) {
        T entity = remove(id);
        return entity;
    }

    @Override
    public T findById(Long id) {
        return getEntity(id);
    }

    @Override
    public List<T> findAllByMachine(MachineInfo machineInfo) {
        Map<Long, T> entities = getByMachine(machineInfo);
        if (entities == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entities.values());
    }

    @Override
    public List<T> findAllByApp(String appName) {
        AssertUtil.notEmpty(appName, "appName cannot be empty");
        Map<Long, T> entities = getByApp(appName);
        if (entities == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entities.values());
    }

    public void clearAll() {
        clear();
    }

    protected T preProcess(T entity) {
        return entity;
    }

    /**
     * Get next unused id.
     *
     * @return next unused id
     */
     protected long nextId() {
         StringBuilder builder=new StringBuilder();
         builder.append("select  id from "+ getTableName() +" order by id DESC limit 1 ");
         Query query= em.createNativeQuery(builder.toString());
         try {
             Long id=  Long.valueOf(query.getSingleResult().toString());
             return ++id;
         }
         catch (NoResultException e){
             clearAll();
             return 1;
         }
     }

    public abstract String getEntityName()
    ;
}
