package com.wfc.starter.wxmp.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 飞影
 * @create by 2020-03-28 22:20
 */
@Data
@ConfigurationProperties(prefix = "wfc.wxmp")
public class WxmpProperties {

    private String appId;
    private String appSecret;

}
