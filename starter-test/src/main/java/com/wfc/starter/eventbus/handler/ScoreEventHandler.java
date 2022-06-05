package com.wfc.starter.eventbus.handler;

import com.google.common.eventbus.Subscribe;
import com.wfc.starter.eventbus.WfcEventHandler;
import com.wfc.starter.eventbus.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 飞影
 * @create by 2020-10-27 22:18
 */
@WfcEventHandler
@Component
@Slf4j
public class ScoreEventHandler {

    @Subscribe
    public void registerGift(UserCreatedEvent event) {
        log.info("handle event id: {}", event.getId());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
