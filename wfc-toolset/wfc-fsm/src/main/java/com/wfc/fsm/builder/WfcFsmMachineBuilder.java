package com.wfc.fsm.builder;

import com.wfc.fsm.FsmMachine;
import com.wfc.fsm.FsmState;
import com.wfc.fsm.WfcFsmMachine;
import com.wfc.fsm.WfcFsmMachineFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hui.guo
 * @since 2022/7/13 6:00 下午
 */
public class WfcFsmMachineBuilder<S, E, C> implements FsmMachineBuilder<S, E, C> {

    private final FsmMachine<S, E, C> fsmMachine;
    private final Map<S, FsmState<S, E, C>> stateMap;

    public WfcFsmMachineBuilder(String machineId) {
        stateMap = new HashMap<>();
        fsmMachine = new WfcFsmMachine<>(machineId, stateMap);
    }

    @Override
    public FsmBuildExternalTransition<S, E, C> externalTransition() {
        return new WfcFsmTransitionBuilder<>(stateMap);
    }

    @Override
    public FsmMachine<S, E, C> build() {
        WfcFsmMachineFactory.register(fsmMachine);
        return fsmMachine;
    }
}
