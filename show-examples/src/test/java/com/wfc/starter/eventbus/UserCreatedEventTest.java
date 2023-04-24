package com.wfc.starter.eventbus;

import com.wfc.eventbus.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 飞影
 * @create by 2020-10-27 22:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Slf4j
public class UserCreatedEventTest {

    @Autowired
    private WfcEventBus wfcEventBus;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            wfcEventBus.post(new UserCreatedEvent((long) i, "aaa"));
            log.info("post event, id: {}", i);
        }
        Thread.sleep(1000);
    }

}
