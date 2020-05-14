package com.xxy.rbac_cloud_service_platform.repository;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMetric;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MetricEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository("jpaMetricsRepository")
public class JpaMetricsRepository implements MetricsRepository<MetricEntity> {
 
   @PersistenceContext
   private EntityManager em;
 
   @Override
   public void save(MetricEntity metric) {
      if (metric == null || StringUtil.isBlank(metric.getApp())) {
         return;
      }
 
 
      ServiceMetric metricPO = new ServiceMetric();
      BeanUtils.copyProperties(metric, metricPO);
 
      StringBuilder hql = new StringBuilder();
      hql.append("FROM ServiceMetric");
      hql.append(" WHERE resource=:resource");
      hql.append(" AND timestamp>=:startTime");
      hql.append(" AND timestamp<=:endTime");
      Query query = em.createQuery(hql.toString());
      query.setParameter("resource", metricPO.getResource());
      query.setParameter("startTime", Date.from(Instant.ofEpochMilli(metricPO.getTimestamp().getTime() - (metricPO.getTimestamp().getSeconds() * 1000))));
      query.setParameter("endTime", Date.from(Instant.ofEpochMilli(metricPO.getTimestamp().getTime() - (metricPO.getTimestamp().getSeconds() * 1000) + 59 * 1000)));
      query.setMaxResults(1);
      List<ServiceMetric> metricPOs = query.getResultList();
      if (CollectionUtils.isEmpty(metricPOs)) {
         em.persist(metricPO);
      } else {
         ServiceMetric saveVo = metricPOs.get(0);
         saveVo.setPassQps(metricPO.getPassQps() + saveVo.getPassQps());
         saveVo.setBlockQps(metricPO.getBlockQps() + saveVo.getBlockQps());
         saveVo.setSuccessQps(metricPO.getSuccessQps() + saveVo.getSuccessQps());
         saveVo.setExceptionQps(metricPO.getExceptionQps() + saveVo.getExceptionQps());
         saveVo.setRt(metricPO.getRt() + saveVo.getRt());
         saveVo.setCount(metricPO.getCount() + saveVo.getCount());
         saveVo.setTimestamp(metricPO.getTimestamp());
         saveVo.setGmtModified(metricPO.getGmtModified());
         em.merge(saveVo);
      }
   }
 
   @Override
   public void saveAll(Iterable<MetricEntity> metrics) {
      if (metrics == null) {
         return;
      }
      removeAll();
      metrics.forEach(this::save);
   }
 
   @Override
   public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
      List<MetricEntity> results = new ArrayList<MetricEntity>();
      return results;
   }
 
   @Override
   public List<MetricEntity> queryByTime(Integer pageIndex,Integer pageSize,String key) {
      List<MetricEntity> results = new ArrayList<MetricEntity>();
 
      StringBuilder hql = new StringBuilder();
      hql.append("FROM MetricPO");
      hql.append(" WHERE timestamp<=:endTime");
      if (StringUtil.isNotBlank(key)){
         hql.append(" AND resource LIKE :key ");
      }
      hql.append(" order by timestamp desc");
      Query query = em.createQuery(hql.toString());
      query.setMaxResults(pageSize);
      query.setFirstResult((pageIndex-1)*pageSize);
      query.setParameter("endTime", Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
      if (StringUtil.isNotBlank(key)){
         query.setParameter("key","%"+key+"%");
      }
      List<ServiceMetric> metricPOs = query.getResultList();
      if (CollectionUtils.isEmpty(metricPOs)) {
         return results;
      }
 
      for (ServiceMetric metricPO : metricPOs) {
         if (metricPO.getGmtCreate().after(new Date(System.currentTimeMillis() - 6 * 3600 * 1000))) {
            MetricEntity metricEntity = new MetricEntity();
            BeanUtils.copyProperties(metricPO, metricEntity);
            results.add(metricEntity);
         }
      }
 
      return results;
   }
 
   @Override
   public Integer countByTime(String key) {
      Integer totalCount = 0;
 
      StringBuilder hql = new StringBuilder();
      hql.append("FROM MetricPO");
      hql.append(" WHERE timestamp<=:endTime");
      if (StringUtil.isNotBlank(key)){
         hql.append(" AND resource LIKE :key ");
      }
      Query query = em.createQuery(hql.toString());
      query.setParameter("endTime", Date.from(Instant.ofEpochMilli(System.currentTimeMillis())));
      if (StringUtil.isNotBlank(key)){
         query.setParameter("key","%"+key+"%");
      }
      List<ServiceMetric> metricPOs = query.getResultList();
      if (CollectionUtils.isEmpty(metricPOs)) {
         return totalCount;
      }
 
      for (ServiceMetric metricPO : metricPOs) {
         if (metricPO.getGmtCreate().after(new Date(System.currentTimeMillis() - 6 * 3600 * 1000))) {
            MetricEntity metricEntity = new MetricEntity();
            BeanUtils.copyProperties(metricPO, metricEntity);
            totalCount++;
         }
      }
 
      return totalCount;
   }
 
   @Override
   public void removeAll() {
      StringBuilder hql = new StringBuilder();
      hql.append("FROM MetricPO");
      hql.append(" WHERE timestamp<:time");
      Query query = em.createQuery(hql.toString());
      query.setParameter("time", Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 6 * 3600 * 1000)));
      List<ServiceMetric> metricPOs = query.getResultList();
      if (CollectionUtils.isEmpty(metricPOs)) {
         return;
      }
      for (ServiceMetric metricPO : metricPOs) {
         //超过6个小时则删除
         em.remove(metricPO);
      }
   }
 
   @Override
   public List<String> listResourcesOfApp(String app) {
      return null;
   }
}