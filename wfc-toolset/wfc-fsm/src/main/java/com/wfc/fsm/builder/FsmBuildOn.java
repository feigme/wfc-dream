package com.wfc.fsm.builder;

import com.wfc.fsm.FsmAction;

/**
 * @author hui.guo
 * @since 2022/7/13 5:19 下午
 */
public interface FsmBuildOn<S, E, C> {

    /**
     * 操作
     *
     * @param fsmAction
     */
    void action(FsmAction<S, E, C> fsmAction);
}
