package com.wfc.starter.mybatis.autoconfigure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author 飞影
 * @create by 2020-08-17 19:43
 */
@Configuration
@EnableConfigurationProperties(WfcMybatisProperties.class)
public class DataSourceConfig {

    @Autowired
    private WfcMybatisProperties wfcMybatisProperties;

    @Primary
    @Bean
    @ConfigurationProperties("datasource.druid")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

}
