package com.xxy.rbac_cloud_upms_biz;

import com.xxy.common.security.annotation.EnableRbacFeignClients;
import com.xxy.common.security.annotation.EnableRbacResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableRbacResourceServer
@EnableRbacFeignClients
@SpringCloudApplication
public class RbacAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacAdminApplication.class,args);
    }
}
