package com.wfc.starter.swagger.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 飞影
 * @create by 2020-08-18 10:31
 */
@Data
@ConfigurationProperties(prefix = "wfc.swagger")
public class SwaggerProperties {

    private Boolean enabled;

    /**
     * 标题
     */
    private String title;

    /**
     * 基本包
     */
    private String basePackage;

    /**
     * 描述
     */
    private String description;

    /**
     * URL
     */
    private String url;

    /**
     * 版本
     */
    private String version;

}
