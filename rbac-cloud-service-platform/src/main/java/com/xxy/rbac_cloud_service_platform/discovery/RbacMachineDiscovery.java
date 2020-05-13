package com.xxy.rbac_cloud_service_platform.discovery;

import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceApp;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMachineInfo;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMetric;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MachineEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Instant;
import java.util.*;

@Component("rbacMachineDiscovery")
public class RbacMachineDiscovery implements MachineDiscovery {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<String> getAppNames() {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct app from service_machineInfo ");

        Query query = em.createQuery(sql.toString());
        List<String> names = query.getResultList();

        return names;
    }

    @Override
    public Set<AppInfo> getBriefApps() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  app,app_type from service_app ");

        Query query = em.createQuery(sql.toString());

        List<ServiceApp> apps = query.getResultList();
        Set<AppInfo> appInfos=new HashSet(apps);

        return appInfos;
    }

    @Override
    public AppInfo getDetailApp(String app) {
        AssertUtil.assertNotBlank(app, "app name cannot be blank");
        StringBuilder hql = new StringBuilder();
        hql.append("select  app,app_type from service_app");
        hql.append(" WHERE app=:app");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", app);
        AppInfo appInfo =(AppInfo) query.getSingleResult();

        return appInfo;
    }

    @Override
    public void removeApp(String app) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM service_app");
        hql.append(" WHERE app=:app");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", app);
        ServiceApp serviceApp =(ServiceApp) query.getSingleResult();
        em.remove(serviceApp);
return ;
    }

    @Override
    public long addMachine(MachineInfo machineInfo) {
        ServiceMachineInfo machineEntity = new ServiceMachineInfo();
        machineEntity.setApp(machineEntity.getApp());
        machineEntity.setGmtCreate(Calendar.getInstance().getTime());
        machineEntity.setHostName(machineInfo.getHostname());
        machineEntity.setIp(machineInfo.getIp());
        machineEntity.setAppType(machineInfo.getAppType());
        machineEntity.setLastHeartbeat(machineInfo.getLastHeartbeat());
        machineEntity.setHeartbeatVersion(machineInfo.getHeartbeatVersion());
        machineEntity.setPort(machineInfo.getPort());
        em.persist(machineEntity);
        return 0;
    }

    @Override
    public boolean removeMachine(String app, String ip, int port) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM service_MachineInfo");
        hql.append(" WHERE app=:app");
        hql.append(" AND ip=:ip ");
        hql.append(" AND port=:port ");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", app);
        query.setParameter("ip", ip);
        query.setParameter("port", port);
        ServiceMachineInfo machineInfo = (ServiceMachineInfo) query.getSingleResult();
        if (machineInfo == null) {
            return false;
        }
        em.remove(machineInfo);
        return true;
    }
}
