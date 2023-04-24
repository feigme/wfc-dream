package com.wfc.starter.eventbus.autoconfigure;


import com.wfc.eventbus.EventBusStartListener;
import com.wfc.eventbus.EventBusStopListener;
import com.wfc.eventbus.WfcEventBus;
import com.wfc.eventbus.config.EventBusConfig;
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
public class EventBusConfigure {

    @Autowired
    private EventBusProperties eventBusProperties;

    @Bean
    public WfcEventBus eventBus() {
        EventBusConfig config = new EventBusConfig();
        config.setCorePoolSize(eventBusProperties.getCorePoolSize());
        config.setKeepAliveTime(eventBusProperties.getKeepAliveTime());
        config.setMaxPoolSize(eventBusProperties.getMaxPoolSize());
        config.setMaxQueueSize(eventBusProperties.getMaxQueueSize());
        return new WfcEventBus(config);
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
