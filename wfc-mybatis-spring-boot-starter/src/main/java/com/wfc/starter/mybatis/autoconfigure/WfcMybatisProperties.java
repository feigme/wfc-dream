package com.wfc.starter.mybatis.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 飞影
 * @create by 2020-09-29 22:26
 */
@Data
@ConfigurationProperties(prefix = "wfc.mybatis")
public class WfcMybatisProperties {

    private DbProperties datasource;
    private DbInit init;

    @Data
    public static class DbProperties {
        private String driver;
        private String url;
        private String username;
        private String password;
    }

    @Data
    public static class DbInit {
        private String schema;
        private String data;
    }

}
