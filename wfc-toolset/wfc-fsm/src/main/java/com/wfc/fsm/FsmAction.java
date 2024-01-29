package com.wfc.fsm;

/**
 * 动作，到达某个状态之后，可以做什么
 *
 * @author hui.guo
 * @since 2022/7/13 3:31 下午
 */
public interface FsmAction<S, E, C> {

    /**
     * 执行
     *
     * @param fromStateId
     * @param toStateId
     * @param event
     * @param ctx
     */
    void exec(S fromStateId, S toStateId, E event, C ctx);

}
