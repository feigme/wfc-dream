package com.wfc.starter.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wfc.starter.eventbus.event.WfcEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 飞影
 * @create by 2020-10-27 22:01
 */
@Slf4j
@Component
public class WfcEventBus {

    private final ThreadPoolExecutor executor;
    private final EventBus eventBus;
    private int corePoolSize = 5;
    private int maxPoolSize = 10;
    private int maxQueueSize = 10000;
    private long keepAliveTime = 5L;

    public WfcEventBus() {
        executor = newExecutor();
        eventBus = new AsyncEventBus(executor,
                (ex,
                 context) -> log.error("EventBusError, post event exception, event={}, handler={}",
                        context.getEvent(), context.getSubscriber(), ex));
    }

    private ThreadPoolExecutor newExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("eventbus-%d").build();
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(maxQueueSize), namedThreadFactory,
                (r,
                 executor1) -> log.error("EventBusError, event task was rejected, task={}, poolStatus={}",
                        r, poolStatus()));
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
