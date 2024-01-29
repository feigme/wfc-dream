package com.wfc.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:17 下午
 */
public interface FsmBuildFrom<S, E, C> {

    /**
     * 目标状态
     *
     * @param s
     *
     * @return
     */
    FsmBuildTo<S, E, C> to(S s);

}
