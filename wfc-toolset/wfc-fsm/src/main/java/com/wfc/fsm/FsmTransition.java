package com.wfc.fsm;

/**
 * 流转，表示从一个状态到另一个状态
 *
 * @author hui.guo
 * @since 2022/7/13 3:04 下午
 */
public interface FsmTransition<S, E, C> {

    /**
     * 原状态
     *
     * @return
     */
    FsmState<S, E, C> getSource();

    /**
     * 设置原状态
     *
     * @param fsmState
     */
    void setSource(FsmState<S, E, C> fsmState);

    /**
     * 目标状态
     *
     * @return
     */
    FsmState<S, E, C> getTarget();

    /**
     * 设置目标状态
     *
     * @param fsmState
     */
    void setTarget(FsmState<S, E, C> fsmState);

    /**
     * 获取事件
     *
     * @return
     */
    E getEvent();

    /**
     * 设置事件
     *
     * @param event
     */
    void setEvent(E event);

    /**
     * 执行动作
     *
     * @return
     */
    FsmAction<S, E, C> getAction();

    /**
     * 设置执行动作
     *
     * @param fsmAction
     */
    void setAction(FsmAction<S, E, C> fsmAction);

    /**
     * do
     *
     * @param c
     *
     * @return
     */
    FsmState<S, E, C> transit(C c);
}
