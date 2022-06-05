package com.wfc.starter.eventbus.handler;

import com.google.common.eventbus.Subscribe;
import com.wfc.starter.eventbus.WfcEventHandler;
import com.wfc.starter.eventbus.event.DemoUserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author hui.guo
 * @since 2022/6/5 10:30 下午
 */
@Component
@WfcEventHandler
@Slf4j
public class DemoUserEventHandler {

    @Subscribe
    public void registerGift(DemoUserCreatedEvent event) {
        log.info("handle event id: {}", event.getId());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }
}
