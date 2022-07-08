package com.wfc.starter.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author 飞影
 * @create by 2020-10-27 22:23
 */
@Slf4j
public class EventBusStopListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private WfcEventBus wfcEventBus;

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        log.info("[wfcEventBus] begin to close");
        long s = System.currentTimeMillis();
        log.info("[wfcEventBus] queue size: {}", wfcEventBus.queueSize());
        while (wfcEventBus.queueSize() > 0) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                log.error("EventBus stop error", e);
            }
        }

        log.info("[wfcEventBus] end to close, spent: {}ms", System.currentTimeMillis() - s);
    }

}
