package com.wfc.starter.fsm;

/**
 * @author hui.guo
 * @since 2022/7/13 3:35 下午
 */
public interface FsmContext<S, E, C> {

    /**
     * 获取Transition
     *
     * @return
     */
    FsmTransition<S, E, C> getTransition();

}
