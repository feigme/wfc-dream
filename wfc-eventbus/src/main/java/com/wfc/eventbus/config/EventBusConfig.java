package com.wfc.eventbus.config;

import lombok.Data;

/**
 * @author hui.guo
 * @since 2023/4/24 6:52 下午
 */
@Data
public class EventBusConfig {

    private int corePoolSize = 5;
    private int maxPoolSize = 10;
    private int maxQueueSize = 1000;
    private long keepAliveTime = 5L;

}
