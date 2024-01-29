package com.wfc.fsm;

import java.util.List;

/**
 * 状态
 *
 * @author hui.guo
 * @since 2022/7/13 3:01 下午
 */
public interface FsmState<S, E, C> {

    /**
     * 返回stateId
     *
     * @return
     */
    S getStateId();

    /**
     * 获取Transition
     *
     * @param event
     *
     * @return
     */
    FsmTransition<S, E, C> getTransition(E event);

    /**
     * 添加Transition
     *
     * @param event
     * @param transition
     */
    void addTransition(E event, FsmTransition<S, E, C> transition);

    /**
     * 所有的Transition
     *
     * @return
     */
    List<FsmTransition<S, E, C>> getAllTransition();
}
