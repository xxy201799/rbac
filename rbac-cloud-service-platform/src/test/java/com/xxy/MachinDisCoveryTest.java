package com.xxy;

import com.xxy.rbac_cloud_service_platform.RbacCloudServiceApplication;
import com.xxy.rbac_cloud_service_platform.datasource.entity.MetricEntity;
import com.xxy.rbac_cloud_service_platform.discovery.AppInfo;
import com.xxy.rbac_cloud_service_platform.discovery.MachineDiscovery;
import com.xxy.rbac_cloud_service_platform.discovery.MachineInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RbacCloudServiceApplication.class)
public class MachinDisCoveryTest {
    @Qualifier("rbacMachineDiscovery")
    @Autowired
    private MachineDiscovery machineDiscovery;
    @Test
    public void testSaveMertic(){

        List<String>  list=machineDiscovery.getAppNames();
        System.out.println(1);
    }
}
