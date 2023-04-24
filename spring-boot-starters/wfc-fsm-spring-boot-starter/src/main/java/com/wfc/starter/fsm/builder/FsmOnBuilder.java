package com.wfc.starter.fsm.builder;

import com.wfc.starter.fsm.FsmCondition;

/**
 * @author hui.guo
 * @since 2022/7/13 5:19 下午
 */
public interface FsmOnBuilder<S, E, C> {

    /**
     * 条件
     *
     * @param fsmCondition
     *
     * @return
     */
    FsmWhenBuilder<S, E, C> when(FsmCondition<C> fsmCondition);
}
