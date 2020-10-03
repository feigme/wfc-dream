package com.wfc.starter.auth.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 飞影
 * @create by 2020-04-12 00:13
 */
@Data
@ConfigurationProperties(prefix = "wfc.auth.jwt")
public class JwtProperties {

    /**
     * jwt 加密 key
     */
    private String key = "wfc4hda8hd2bwds492";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;

}
