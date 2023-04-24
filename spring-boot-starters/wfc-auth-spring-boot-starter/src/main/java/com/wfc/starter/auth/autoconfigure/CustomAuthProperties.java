package com.wfc.starter.auth.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 飞影
 * @create by 2020-09-19 21:49
 */
@Data
@ConfigurationProperties(prefix = "wfc.auth")
public class CustomAuthProperties {

    private final IgnorePath ignore = new IgnorePath();

    private List<String> loginAndRegisterUrl = new ArrayList();

    @Data
    public static class IgnorePath {
        /**
         * 需要忽略的 GET 请求
         */
        private List<String> get = new ArrayList();

        /**
         * 需要忽略的 POST 请求
         */
        private List<String> post = new ArrayList();

        /**
         * 需要忽略的 DELETE 请求
         */
        private List<String> delete = new ArrayList();

        /**
         * 需要忽略的 PUT 请求
         */
        private List<String> put = new ArrayList();
    }
}


