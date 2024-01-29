package com.wfc.fsm.builder;

import com.wfc.fsm.FsmMachine;

/**
 * @author hui.guo
 * @since 2022/7/13 5:30 下午
 */
public interface FsmMachineBuilder<S, E, C> {

    /**
     * builder one
     *
     * @return
     */
    FsmBuildExternalTransition<S, E, C> externalTransition();

    /**
     * build FSM
     *
     * @return
     */
    FsmMachine<S, E, C> build();
}
