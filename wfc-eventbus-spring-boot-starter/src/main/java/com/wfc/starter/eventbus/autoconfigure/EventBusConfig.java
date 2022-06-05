package com.wfc.starter.eventbus.autoconfigure;

import com.wfc.starter.eventbus.EventBusStartListener;
import com.wfc.starter.eventbus.EventBusStopListener;
import com.wfc.starter.eventbus.WfcEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hui.guo
 * @since 2022/6/5 8:32 下午
 */
@Configuration
public class EventBusConfig {

    @Bean
    public WfcEventBus eventBus() {
        return new WfcEventBus();
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
