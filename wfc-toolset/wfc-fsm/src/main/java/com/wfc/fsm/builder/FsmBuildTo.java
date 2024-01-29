package com.wfc.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:18 下午
 */
public interface FsmBuildTo<S, E, C> {

    /**
     * 事件
     *
     * @param e
     *
     * @return
     */
    FsmBuildOn<S, E, C> on(E e);

}
