package com.wfc.fsm;

/**
 * 状态机
 *
 * @author hui.guo
 * @since 2022/7/13 3:36 下午
 */
public interface FsmMachine<S, E, C> {

    /**
     * 发送事件
     *
     * @param state
     * @param event
     * @param ctx
     *
     * @return
     */
    S fireEvent(S state, E event, C ctx);

    /**
     * FSM id
     *
     * @return
     */
    String getMachineId();

}
