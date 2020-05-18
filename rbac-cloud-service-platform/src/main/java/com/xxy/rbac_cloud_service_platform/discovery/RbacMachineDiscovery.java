package com.xxy.rbac_cloud_service_platform.discovery;

import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.xxy.common.core.util.SpringUtil;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceApp;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMachineInfo;
import com.xxy.rbac_cloud_service_platform.database.entity.ServiceMetric;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MachineEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
        sql.append("select  app from ServiceApp ");

        Query query = em.createQuery(sql.toString());
        List<String> names = query.getResultList();

        return names;
    }

    @Override
    public Set<AppInfo> getBriefApps() {
        StringBuilder sql = new StringBuilder();
        sql.append("select  app,appType from ServiceApp ");
        Query query = em.createQuery(sql.toString());
        List<AppInfo> apps = query.getResultList();
        Set<AppInfo> appInfos = new HashSet(apps);
// 对app的machine进行赋值
        for (AppInfo app : apps) {
            StringBuilder machinesql = new StringBuilder();
            machinesql.append("select  app,appType,hostname,ip,port,lastHeartbeat,heartbeatVersion from ServiceMachineInfo ");
            machinesql.append("WHERE app=:app");
            Query machinequery = em.createQuery(machinesql.toString());
            machinequery.setParameter("app", app);
            List<MachineInfo> machineInfos = query.getResultList();
            machineInfos.stream().map(m -> app.addMachine(m));
        }
        return appInfos;
    }

    @Override
    public AppInfo getDetailApp(String app) {
        //找出唯一APP
        AssertUtil.assertNotBlank(app, "app name cannot be blank");
        StringBuilder hql = new StringBuilder();
        hql.append(" from ServiceApp  ");
        hql.append(" WHERE app=:app");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", app);
        AppInfo appInfo = (AppInfo) query.getSingleResult();
//对APP中的machineinfo进行赋值

        StringBuilder machinesql = new StringBuilder();
        machinesql.append("select  app,appType,hostname,ip,port,lastHeartbeat,heartbeatVersion from ServiceMachineInfo ");
        machinesql.append("WHERE app=:app");
        Query machinequery = em.createQuery(machinesql.toString());
        machinequery.setParameter("app", app);
        List<MachineInfo> machineInfos = query.getResultList();
        machineInfos.stream().map(m -> appInfo.addMachine(m));
        return appInfo;
    }

    @Override
    public void removeApp(String app) {
        //删除app
        StringBuilder apphql = new StringBuilder();
        apphql.append("FROM ServiceApp");
        apphql.append(" WHERE app=:app");
        Query appQuery = em.createQuery(apphql.toString());
        appQuery.setParameter("app", app);
        ServiceApp serviceApp = (ServiceApp) appQuery.getSingleResult();
        em.remove(serviceApp);
        //删除所有匹配的机器
        StringBuilder hql = new StringBuilder();
        hql.append("FROM ServiceMachineInfo");
        hql.append(" WHERE app=:app");
        Query query = em.createQuery(hql.toString());
        query.setParameter("app", app);
        List<ServiceMachineInfo> machineInfos = query.getResultList();

        for (ServiceMachineInfo machineInfo : machineInfos) {
            em.remove(machineInfo);
        }
        return;
    }

    @Override
    @Transactional
    public long addMachine(MachineInfo machineInfo) {
        ServiceMachineInfo machineEntity = new ServiceMachineInfo();
        SpringUtil.copyPropertiesIgnoreNull(machineInfo, machineEntity);
        em.persist(machineEntity);
        return 0;
    }

    @Override
    @Transactional
    public boolean removeMachine(String app, String ip, int port) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM ServiceMachineInfo");
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
