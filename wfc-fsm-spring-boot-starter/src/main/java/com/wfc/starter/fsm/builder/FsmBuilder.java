package com.wfc.starter.fsm.builder;

import com.wfc.starter.fsm.FsmMachine;

/**
 * @author hui.guo
 * @since 2022/7/13 5:30 下午
 */
public interface FsmBuilder<S, E, C> {

    /**
     * builder one
     *
     * @return
     */
    FsmExternalTransitionBuilder<S, E, C> externalTransition();

    /**
     * 构建状态机
     *
     * @param fsmName
     *
     * @return
     */
    FsmMachine<S, E, C> build(String fsmName);

}
