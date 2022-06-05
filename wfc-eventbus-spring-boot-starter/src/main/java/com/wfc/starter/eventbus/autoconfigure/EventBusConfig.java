package com.wfc.starter.eventbus.autoconfigure;

import com.wfc.starter.eventbus.EventBusStartListener;
import com.wfc.starter.eventbus.EventBusStopListener;
import com.wfc.starter.eventbus.WfcEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hui.guo
 * @since 2022/6/5 8:32 下午
 */
@Configuration
@EnableConfigurationProperties(EventBusProperties.class)
public class EventBusConfig {

    @Autowired
    private EventBusProperties eventBusProperties;

    @Bean
    public WfcEventBus eventBus() {
        return new WfcEventBus(eventBusProperties);
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
