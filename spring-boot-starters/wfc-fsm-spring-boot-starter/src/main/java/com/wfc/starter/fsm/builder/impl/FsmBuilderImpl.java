package com.wfc.starter.fsm.builder.impl;

import com.wfc.starter.fsm.FsmFactory;
import com.wfc.starter.fsm.FsmMachine;
import com.wfc.starter.fsm.builder.FsmBuilder;
import com.wfc.starter.fsm.builder.FsmExternalTransitionBuilder;
import com.wfc.starter.fsm.impl.FsmMachineImpl;

/**
 * @author hui.guo
 * @since 2022/7/13 6:00 下午
 */
public class FsmBuilderImpl<S, E, C> implements FsmBuilder<S, E, C> {

    @Override
    public FsmExternalTransitionBuilder<S, E, C> externalTransition() {
        return new FsmTransitionBuilderImpl<>();
    }

    @Override
    public FsmMachine<S, E, C> build(String fsmName) {
        final FsmMachine<S, E, C> fsmMachine = new FsmMachineImpl(fsmName);
        FsmFactory.register(fsmMachine);
        return fsmMachine;
    }
}
