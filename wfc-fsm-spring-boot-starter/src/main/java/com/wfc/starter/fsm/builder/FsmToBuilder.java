package com.wfc.starter.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:18 下午
 */
public interface FsmToBuilder<S, E, C> {

    /**
     * 事件
     *
     * @param e
     *
     * @return
     */
    FsmOnBuilder<S, E, C> on(E e);

}
