package com.wfc.starter.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:37 下午
 */
public interface FsmExternalTransitionBuilder<S, E, C> {

    /**
     * 原状态
     * @param s
     * @return
     */
    FsmFromBuilder from(S s);

}
