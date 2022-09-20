package com.wfc.starter.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 5:17 下午
 */
public interface FsmFromBuilder<S, E, C> {

    /**
     * 目标状态
     *
     * @param s
     * @return
     */
    FsmToBuilder<S, E, C> to(S s);

}
