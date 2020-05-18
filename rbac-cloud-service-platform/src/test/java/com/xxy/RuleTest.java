package com.xxy;

import com.xxy.rbac_cloud_service_platform.RbacCloudServiceApplication;
import com.xxy.rbac_cloud_service_platform.database.entity.rule.ServiceFlowRule;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MetricEntity;
import com.xxy.rbac_cloud_service_platform.datasource.entity.rule.FlowRuleEntity;
import com.xxy.rbac_cloud_service_platform.repository.rule.RdbcRuleRepositoryAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RbacCloudServiceApplication.class)

public class RuleTest {
    @Autowired
    private RdbcRuleRepositoryAdapter<FlowRuleEntity, ServiceFlowRule> repository;
    @PersistenceContext
    private EntityManager em;
    @Test
    public void testSaveMertic(){
        FlowRuleEntity flowRuleEntity = new FlowRuleEntity();

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(" from "+"ServiceFlowRule");
//        stringBuilder.append(" where id=:id");
//        Query query = em.createQuery(stringBuilder.toString());
//        query.setParameter("id", Long.valueOf(1));
//        ServiceFlowRule entity = (ServiceFlowRule) query.getSingleResult();
        System.out.println(1);
        flowRuleEntity.setApp("222222222222");
        flowRuleEntity.setIp("33333333333");

      //  repository.delete(Long.valueOf(1));
      //  repository.save(flowRuleEntity);
    List<FlowRuleEntity> flowRuleEntities= repository.findAllByApp("222222222222");
        System.out.println(1);

    }

}
