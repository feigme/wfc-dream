package com.wfc.fsm.builder;

import com.wfc.fsm.FsmAction;
import com.wfc.fsm.FsmState;
import com.wfc.fsm.FsmTransition;
import com.wfc.fsm.WfcFsmState;
import com.wfc.fsm.WfcFsmTransition;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2022/7/13 6:01 下午
 */
public class WfcFsmTransitionBuilder<S, E, C> implements FsmBuildExternalTransition<S, E, C>,
        FsmBuildFrom<S, E, C>, FsmBuildTo<S, E, C>, FsmBuildOn<S, E, C> {

    private final FsmTransition<S, E, C> transition;
    private final Map<S, FsmState<S, E, C>> stateMap;

    public WfcFsmTransitionBuilder(Map<S, FsmState<S, E, C>> stateMap) {
        this.transition = new WfcFsmTransition<>();
        this.stateMap = stateMap;
    }

    @Override
    public FsmBuildFrom<S, E, C> from(S stateId) {
        transition.setSource(getOrCreateState(stateId));
        return this;
    }

    @Override
    public FsmBuildTo<S, E, C> to(S stateId) {
        transition.setTarget(getOrCreateState(stateId));
        return this;
    }

    @Override
    public FsmBuildOn<S, E, C> on(E event) {
        transition.setEvent(event);
        transition.getSource().addTransition(event, transition);
        return this;
    }

    @Override
    public void action(FsmAction<S, E, C> fsmAction) {
        transition.setAction(fsmAction);
    }

    private FsmState<S, E, C> getOrCreateState(S stateId) {
        FsmState<S, E, C> fsmState = this.stateMap.get(stateId);
        if (fsmState == null) {
            fsmState = new WfcFsmState<>(stateId);
            this.stateMap.put(stateId, fsmState);
        }
        return fsmState;
    }
}
