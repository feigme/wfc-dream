package com.wfc.starter.fsm.builder;

import com.wfc.starter.fsm.FsmAction;

/**
 * @author hui.guo
 * @since 2022/7/13 5:21 下午
 */
public interface FsmWhenBuilder<S, E, C> {

    /**
     * 操作
     *
     * @param fsmAction
     */
    void action(FsmAction<S, E, C> fsmAction);

}
