package com.wfc.starter.fsm.builder.impl;

import com.wfc.starter.fsm.FsmAction;
import com.wfc.starter.fsm.FsmCondition;
import com.wfc.starter.fsm.FsmTransition;
import com.wfc.starter.fsm.builder.FsmExternalTransitionBuilder;
import com.wfc.starter.fsm.builder.FsmFromBuilder;
import com.wfc.starter.fsm.builder.FsmOnBuilder;
import com.wfc.starter.fsm.builder.FsmToBuilder;
import com.wfc.starter.fsm.builder.FsmWhenBuilder;
import com.wfc.starter.fsm.impl.FsmStateImpl;
import com.wfc.starter.fsm.impl.FsmTransitionImpl;

/**
 * @author hui.guo
 * @since 2022/7/13 6:01 下午
 */
public class FsmTransitionBuilderImpl<S, E, C> implements FsmExternalTransitionBuilder<S, E, C>,
        FsmFromBuilder<S, E, C>, FsmToBuilder<S, E, C>, FsmOnBuilder<S, E, C>, FsmWhenBuilder<S, E, C> {

    private FsmTransition<S, E, C> transition;

    public FsmTransitionBuilderImpl() {
        transition = new FsmTransitionImpl();
    }

    @Override
    public FsmFromBuilder from(S s) {
        transition.setSource(new FsmStateImpl<>(s));
        return this;
    }

    @Override
    public FsmToBuilder<S, E, C> to(S s) {
        transition.setTarget(new FsmStateImpl<>(s));
        return this;
    }

    @Override
    public FsmWhenBuilder<S, E, C> when(FsmCondition<C> fsmCondition) {
        transition.setCondition(fsmCondition);
        return this;
    }

    @Override
    public FsmOnBuilder<S, E, C> on(E e) {
        transition.setEvent(e);
        return this;
    }

    @Override
    public void action(FsmAction<S, E, C> fsmAction) {
        transition.setAction(fsmAction);
    }
}
