package com.wfc.bpc.core.func;

import com.wfc.bpc.core.BpcContext;

import java.util.Objects;

/**
 * @author hui.guo
 * @since 2022/7/6 9:24 下午
 */
@FunctionalInterface
public interface BpcRollbackFunc<T extends BpcContext> {

    /**
     * 执行组件
     *
     * @param t
     *
     * @return
     */
    T rollback(T t);

    /**
     * 下一个执行的组件
     *
     * @param before
     *
     * @return
     */
    default BpcRollbackFunc<T> andThen(BpcRollbackFunc<T> before) {
        Objects.requireNonNull(before);
        return (T t) -> rollback(before.rollback(t));
    }
}
