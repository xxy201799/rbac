package com.xxy.common.security.service;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class RbacClientDetailsService extends JdbcClientDetailsService {

    public RbacClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

}
