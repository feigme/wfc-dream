package com.wfc.starter.fsm.impl;

import com.wfc.starter.fsm.FsmAction;
import com.wfc.starter.fsm.FsmCondition;
import com.wfc.starter.fsm.FsmState;
import com.wfc.starter.fsm.FsmTransition;

/**
 * @author hui.guo
 * @since 2022/7/13 9:21 下午
 */
public class FsmTransitionImpl<S, E, C> implements FsmTransition<S, E, C> {

    private FsmState<S, E, C> source;
    private FsmState<S, E, C> target;
    private FsmCondition<C> condition;
    private FsmAction<S, E, C> action;
    private E event;

    public FsmTransitionImpl() {

    }

    @Override
    public FsmState<S, E, C> getSource() {
        return this.source;
    }

    @Override
    public void setSource(FsmState<S, E, C> fsmState) {
        this.source = fsmState;
    }

    @Override
    public FsmState<S, E, C> getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(FsmState<S, E, C> fsmState) {
        this.target = fsmState;
    }

    @Override
    public E getEvent() {
        return this.event;
    }

    @Override
    public void setEvent(E e) {
        this.event = e;
    }

    @Override
    public FsmCondition<C> getCondition() {
        return this.condition;
    }

    @Override
    public void setCondition(FsmCondition<C> fsmCondition) {
        this.condition = fsmCondition;
    }

    @Override
    public FsmAction<S, E, C> getAction() {
        return this.action;
    }

    @Override
    public void setAction(FsmAction<S, E, C> fsmAction) {
        this.action = fsmAction;
    }

    @Override
    public FsmState<S, E, C> transit(C c) {
        if (condition == null || condition.isSatisfied(c)) {
            if (action != null) {
                action.exec(source.getStateId(), target.getStateId(), event, c);
            }
            return target;
        }
        return source;
    }
}
