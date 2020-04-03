package com.xxy.rbac_cloud_auth;

import com.xxy.common.security.annotation.EnableRbacFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRbacFeignClients
public class RbacCloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacCloudAuthApplication.class, args);
    }

}
