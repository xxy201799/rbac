package com.xxy;

import com.xxy.rbac_cloud_service_platform.RbacCloudServiceApplication;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MetricEntity;
import com.xxy.rbac_cloud_service_platform.repository.MetricsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: XXY
 * @Date: 2020/5/10 20:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RbacCloudServiceApplication.class)
public class MetricTest {
    @Qualifier("jpaMetricsRepository")
    @Autowired
    private MetricsRepository<MetricEntity> metricStore;
    @Test
    public void testSaveMertic(){
        MetricEntity entity = new MetricEntity();
        entity.setBlockQps(Long.valueOf(1));
        entity.setApp("XXY");
        entity.setTimestamp(new Date());
        metricStore.save(entity);
    }

}
