package com.wfc.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wfc.eventbus.config.EventBusConfig;
import com.wfc.eventbus.event.WfcEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 飞影
 */
@Slf4j
public class WfcEventBus {

    private final ThreadPoolExecutor executor;
    private final EventBus eventBus;


    public WfcEventBus(EventBusConfig conf) {
        executor = new ThreadPoolExecutor(
                conf.getCorePoolSize(),
                conf.getMaxPoolSize(),
                conf.getKeepAliveTime(),
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(conf.getMaxQueueSize()),
                new ThreadFactoryBuilder().setNameFormat("eventbus-%d").build(),
                (r, executor1) -> log.error("[wfcEventBus] EventBusError, event task was rejected, task={}, poolStatus={}", r, poolStatus()));
        eventBus = new AsyncEventBus(executor);
        log.info("[wfcEventBus] init threadPool, {}", conf);
    }

    private Map<String, Number> poolStatus() {
        final Map<String, Number> info = new LinkedHashMap<>(8);
        info.put("queueSize", executor.getQueue().size());
        info.put("activeCount", executor.getActiveCount());
        info.put("taskCount", executor.getTaskCount());
        info.put("completedTaskCount", executor.getCompletedTaskCount());
        info.put("largestPoolSize", executor.getLargestPoolSize());
        info.put("poolSize", executor.getPoolSize());
        info.put("corePoolSize", executor.getCorePoolSize());
        info.put("maximumPoolSize", executor.getMaximumPoolSize());
        return info;
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public void post(WfcEvent event) {
        eventBus.post(event);
        log.debug("[wfcEventBus] sent event: {}", event);
    }

    /**
     * 查询当前queue大小
     *
     * @return
     */
    public int queueSize() {
        return executor.getQueue().size();
    }

    /**
     * 注册handler类
     *
     * @param handler
     */
    public void register(Object handler) {
        eventBus.register(handler);
    }

}
