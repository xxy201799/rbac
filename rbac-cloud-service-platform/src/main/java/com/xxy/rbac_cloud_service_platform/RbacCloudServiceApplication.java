package com.xxy.rbac_cloud_service_platform;

import com.xxy.common.security.annotation.EnableRbacFeignClients;
import com.xxy.common.security.annotation.EnableRbacResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author: XXY
 * @Date: 2020/5/4 19:30
 */
@EnableRbacResourceServer
@EnableRbacFeignClients
@SpringCloudApplication
public class RbacCloudServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacCloudServiceApplication.class, args);
    }
}
