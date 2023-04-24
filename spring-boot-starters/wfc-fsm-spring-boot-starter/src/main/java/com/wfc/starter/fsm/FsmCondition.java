package com.wfc.starter.fsm;

/**
 * @author hui.guo
 * @since 2022/7/13 3:27 下午
 */
public interface FsmCondition<C> {

    /**
     * 条件判断
     *
     * @param c
     *
     * @return
     */
    boolean isSatisfied(C c);

}
