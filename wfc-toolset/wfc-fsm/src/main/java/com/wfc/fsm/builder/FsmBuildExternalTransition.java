package com.wfc.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:37 下午
 */
public interface FsmBuildExternalTransition<S, E, C> {

    /**
     * 原状态
     *
     * @param s
     *
     * @return
     */
    FsmBuildFrom from(S s);

}
