package com.wfc.starter.fsm;

/**
 * @author hui.guo
 * @since 2022/7/13 3:31 下午
 */
public interface FsmAction<S, E, C> {

    /**
     * 执行
     *
     * @param from
     * @param to
     * @param event
     * @param ctx
     */
    void exec(S from, S to, E event, C ctx);

}
