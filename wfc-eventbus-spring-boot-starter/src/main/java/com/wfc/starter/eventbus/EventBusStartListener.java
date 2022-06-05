package com.wfc.starter.eventbus;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 飞影
 * @create by 2020-10-27 22:08
 */
@Slf4j
public class EventBusStartListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WfcEventBus wfcEventBus;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext ctx = contextRefreshedEvent.getApplicationContext();
        if (ctx.getParent() == null) {
            scanWithAnnotation(ctx);
        }
    }

    /**
     * 自动扫描注册eventbus的handler
     *
     * @param ctx
     */
    private void scanWithAnnotation(ApplicationContext ctx) {
        log.info("[wfcEventBus] begin to register");
        long s = System.currentTimeMillis();
        Map<String, Object> map = ctx.getBeansWithAnnotation(WfcEventHandler.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (isEventHandler(entry.getValue())) {
                wfcEventBus.register(entry.getValue());
                log.info("[wfcEventBus] register event handler, bean: {}", entry.getKey());
            }
        }
        log.info("[wfcEventBus] end to register, spent: {}ms", System.currentTimeMillis() - s);
    }

    private boolean isEventHandler(Object bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                return true;
            }
        }
        return false;
    }

}
