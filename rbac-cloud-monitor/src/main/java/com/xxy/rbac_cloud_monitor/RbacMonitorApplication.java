package com.xxy.rbac_cloud_monitor;

import com.xxy.common.security.annotation.EnableRbacFeignClients;
import com.xxy.common.security.annotation.EnableRbacResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @Author: XXY
 * @Date: 2020/5/16 20:20
 */
@EnableRbacResourceServer
@EnableRbacFeignClients
@SpringCloudApplication
public class RbacMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacMonitorApplication.class, args);
    }
}
