package com.wfc.starter.fsm.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.wfc.starter.fsm.FsmState;
import com.wfc.starter.fsm.FsmTransition;

import java.util.List;

/**
 * @author hui.guo
 * @since 2022/7/13 9:09 下午
 */
public class FsmStateImpl<S, E, C> implements FsmState<S, E, C> {

    protected final S s;

    private LinkedListMultimap<E, FsmTransition<S, E, C>> transitionMap = LinkedListMultimap.create();

    public FsmStateImpl(S s) {
        this.s = s;
    }

    @Override
    public S getStateId() {
        return this.s;
    }

    @Override
    public List<FsmTransition<S, E, C>> getTransitions(E event) {
        return Lists.newArrayList(transitionMap.get(event));
    }
}
