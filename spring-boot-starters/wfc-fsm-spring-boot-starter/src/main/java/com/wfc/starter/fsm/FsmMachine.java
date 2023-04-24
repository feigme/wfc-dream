package com.wfc.starter.fsm;

/**
 * fsm
 *
 * @author hui.guo
 * @since 2022/7/13 3:36 下午
 */
public interface FsmMachine<S, E, C> {

    /**
     * 发送事件
     *
     * @param source
     * @param event
     * @param ctx
     * @return
     */
    S fireEvent(S source, E event, C ctx);

    /**
     * FSM id
     * @return
     */
    String getMachineId();
}
