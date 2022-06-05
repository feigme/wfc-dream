package com.wfc.starter.eventbus.event;

import com.wfc.starter.eventbus.WfcEventBus;
import com.wfc.starter.eventbus.autoconfigure.EventBusConfig;
import com.wfc.starter.eventbus.handler.DemoUserEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 飞影
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = {EventBusConfig.class, DemoUserEventHandler.class})
@Slf4j
public class DemoUserCreatedEventTest {

    @Autowired
    private WfcEventBus wfcEventBus;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            wfcEventBus.post(new DemoUserCreatedEvent((long) i, "aaa"));
            log.info("post event, id: {}", i);
        }

        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            log.error("EventBus stop error", e);
        }

    }
}

