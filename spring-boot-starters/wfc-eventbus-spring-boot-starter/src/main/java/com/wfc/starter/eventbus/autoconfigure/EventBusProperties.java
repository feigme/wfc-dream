package com.wfc.starter.eventbus.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hui.guo
 * @since 2022/6/5 11:59 下午
 */
@Data
@ConfigurationProperties(prefix = "wfc.eventbus")
public class EventBusProperties {

    private int corePoolSize = 5;
    private int maxPoolSize = 10;
    private int maxQueueSize = 1000;
    private long keepAliveTime = 5L;

}
