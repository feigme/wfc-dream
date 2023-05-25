package com.wfc.bpc.core;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 上下文
 *
 * @author hui.guo
 * @since 2022/7/1 5:30 下午
 */
public interface BpcContext extends BpcStates {

    /**
     * 获取属性
     *
     * @param <T>
     *
     * @param key
     * @return
     */
    <T> Object getAttribute(String key);

    /**
     * 放入属性
     *
     * @param key
     * @param t
     * @param <T>
     */
    <T> void putAttribute(String key, T t);

    /**
     * 获取属性，值不存在时，可以通过回调获取
     *
     * @param key
     * @param func
     * @param <T>
     *
     * @return
     */
    <T> T getAttribute(String key, Function<String, T> func);

    /**
     * 信息
     *
     * @return
     */
    String getMessage();

    /**
     * 设置消息
     *
     * @param message
     */
    void setMessage(String message);
}