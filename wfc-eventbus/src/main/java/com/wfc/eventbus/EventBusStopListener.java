package com.wfc.eventbus;

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
        log.info("[wfcEventBus] begin to close, queue size: {}", wfcEventBus.queueSize());
        long s = System.currentTimeMillis();
        while (wfcEventBus.queueSize() > 0) {
            try {
                log.info("[wfcEventBus] queue left: {}, pls wait a moment", wfcEventBus.queueSize());
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                log.error("[wfcEventBus] EventBus stop error", e);
            }
        }

        log.info("[wfcEventBus] end to close, spent: {}ms", System.currentTimeMillis() - s);
    }

}
