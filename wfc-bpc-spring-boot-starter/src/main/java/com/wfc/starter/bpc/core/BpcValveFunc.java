package com.wfc.starter.bpc.core;

import java.util.Objects;

/**
 * @author hui.guo
 * @since 2022/7/5 5:10 下午
 */
@FunctionalInterface
public interface BpcValveFunc<T extends BpcContext> {

    /**
     * 执行组件
     *
     * @param t
     *
     * @return
     */
    T invoke(T t);

    /**
     * 下一个执行的组件
     *
     * @param after
     *
     * @return
     */
    default BpcValveFunc<T> andThen(BpcValveFunc<T> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.invoke(invoke(t));
    }
}
