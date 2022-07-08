package com.wfc.starter.bpc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author hui.guo
 * @since 2022/7/5 9:17 下午
 */
public class BpcTodoContext implements BpcContext {

    private Map<String, Object> attributes;
    private boolean broken;
    private boolean finished;

    @Override
    public <T> T getAttribute(String key) {
        if (attributes == null) {
            attributes = new ConcurrentHashMap<>(16);
        }
        return (T) attributes.get(key);
    }

    @Override
    public <T> T getAttribute(String key, Supplier<T> supplier) {
        T t = this.getAttribute(key);
        if (t == null && supplier != null) {
            t = supplier.get();
            if (t != null) {
                attributes.put(key, t);
                return t;
            }
        }
        return t;
    }

    @Override
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    @Override
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean isBroken() {
        return broken;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
