package com.wfc.starter.eventbus.autoconfigure;


import com.wfc.eventbus.EventBusStartListener;
import com.wfc.eventbus.EventBusStopListener;
import com.wfc.eventbus.WfcEventBus;
import com.wfc.eventbus.config.EventBusConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hui.guo
 * @since 2022/6/5 8:32 下午
 */
@Configuration
public class EventBusConfigure {

    @Bean
    @ConfigurationProperties(prefix = "wfc.eventbus")
    public EventBusConfig eventBusConfig() {
        return new EventBusConfig();
    }

    @Bean
    public WfcEventBus eventBus(EventBusConfig eventBusConfig) {
        return new WfcEventBus(eventBusConfig);
    }

    @Bean
    public EventBusStartListener eventBusStartListener() {
        return new EventBusStartListener();
    }

    @Bean
    public EventBusStopListener eventBusStopListener() {
        return new EventBusStopListener();
    }

}
