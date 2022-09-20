package com.wfc.starter.fsm;

import java.util.List;

/**
 * @author hui.guo
 * @since 2022/7/13 3:01 下午
 */
public interface FsmState<S, E, C> {

    /**
     * 返回state
     *
     * @return
     */
    S getStateId();

    /**
     * 获取Transition
     *
     * @param event
     * @return
     */
    List<FsmTransition<S, E, C>> getTransitions(E event);
}
