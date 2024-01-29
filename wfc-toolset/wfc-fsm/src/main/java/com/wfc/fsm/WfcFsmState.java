package com.wfc.fsm;

import com.wfc.fsm.exception.FsmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hui.guo
 * @since 2022/7/13 9:09 下午
 */
public class WfcFsmState<S, E, C> implements FsmState<S, E, C> {

    protected final S stateId;

    private final Map<E, FsmTransition<S, E, C>> transitionMap = new HashMap<>();

    public WfcFsmState(S stateId) {
        this.stateId = stateId;
    }

    @Override
    public S getStateId() {
        return this.stateId;
    }

    @Override
    public FsmTransition<S, E, C> getTransition(E event) {
        return transitionMap.get(event);
    }

    @Override
    public void addTransition(E event, FsmTransition<S, E, C> transition) {
        if (transitionMap.containsKey(event)) {
            throw new FsmException(String.format("event: %s has existed", event));
        }

        transitionMap.put(event, transition);
    }

    @Override
    public List<FsmTransition<S, E, C>> getAllTransition() {
        return new ArrayList<>(transitionMap.values());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FsmState)) {
            return false;
        }
        return this.stateId.equals(((FsmState<?, ?, ?>) obj).getStateId());
    }

    @Override
    public String toString() {
        return this.stateId.toString();
    }
}
