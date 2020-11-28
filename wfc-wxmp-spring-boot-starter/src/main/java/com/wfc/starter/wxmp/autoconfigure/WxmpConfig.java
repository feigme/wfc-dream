package com.wfc.starter.wxmp.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 飞影
 * @create by 2020-11-24 23:29
 */
@Configuration
@EnableConfigurationProperties(WxmpProperties.class)
public class WxmpConfig {

    @Autowired
    private WxmpProperties wxmpProperties;
}
