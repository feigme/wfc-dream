package com.wfc.starter.fsm.impl;

import com.wfc.starter.fsm.FsmMachine;
import com.wfc.starter.fsm.FsmState;
import com.wfc.starter.fsm.FsmTransition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author hui.guo
 * @since 2022/7/13 11:28 下午
 */
@Slf4j
public class FsmMachineImpl<S, E, C> implements FsmMachine<S, E, C> {

    private String machineId;

    public FsmMachineImpl(String machineId){
        this.machineId = machineId;
    }

    @Override
    public S fireEvent(S source, E event, C ctx) {
        FsmTransition<S, E, C> fsmTransition = routeTransition(source, event, ctx);
        if (fsmTransition == null) {
            log.warn("no transition found, event: {}", event);
            return source;
        }

        return fsmTransition.transit(ctx).getStateId();
    }

    @Override
    public String getMachineId() {
        return this.machineId;
    }

    private FsmTransition<S, E, C> routeTransition(S source, E event, C ctx) {
        FsmState sourceFsmState = new FsmStateImpl(source);

        List<FsmTransition<S, E, C>> transitionList = sourceFsmState.getTransitions(event);
        if (CollectionUtils.isEmpty(transitionList)) {
            return null;
        }

        for (FsmTransition<S, E, C> transition : transitionList) {
            if (transition.getCondition() == null || transition.getCondition().isSatisfied(ctx)) {
                return transition;
            }
        }

        return null;
    }
}
