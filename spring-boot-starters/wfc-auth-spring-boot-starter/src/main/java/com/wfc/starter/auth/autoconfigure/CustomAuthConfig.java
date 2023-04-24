package com.wfc.starter.auth.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 飞影
 * @create by 2020-09-19 21:45
 */
@Configuration
@EnableConfigurationProperties(CustomAuthProperties.class)
public class CustomAuthConfig {

    @Autowired
    private CustomAuthProperties customAuthProperties;

}