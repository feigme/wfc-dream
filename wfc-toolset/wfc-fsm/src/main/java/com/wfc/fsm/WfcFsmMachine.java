package com.wfc.fsm;

import com.wfc.fsm.exception.FsmException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2022/7/13 11:28 下午
 */
@Slf4j
public class WfcFsmMachine<S, E, C> implements FsmMachine<S, E, C>, FsmPlant<S, E, C> {

    private final String machineId;

    private final Map<S, FsmState<S, E, C>> stateMap;

    public WfcFsmMachine(String machineId, Map<S, FsmState<S, E, C>> stateMap) {
        this.machineId = machineId;
        this.stateMap = stateMap;
    }

    @Override
    public S fireEvent(S stateId, E event, C ctx) {
        FsmTransition<S, E, C> fsmTransition = routeTransition(stateId, event);
        if (fsmTransition == null) {
            log.warn("no transition found, event: {}", event);
            return stateId;
        }

        return fsmTransition.transit(ctx).getStateId();
    }

    @Override
    public String getMachineId() {
        return this.machineId;
    }

    @Override
    public Map<S, FsmState<S, E, C>> getStateMap() {
        return stateMap;
    }

    private FsmTransition<S, E, C> routeTransition(S stateId, E event) {
        return this.getState(stateId).getTransition(event);
    }

    private FsmState<S, E, C> getState(S stateId) {
        FsmState<S, E, C> fsmState = this.stateMap.get(stateId);
        if (fsmState == null) {
            throw new FsmException(String.format("stateId: %s is not found", stateId));
        }
        return fsmState;
    }
}
