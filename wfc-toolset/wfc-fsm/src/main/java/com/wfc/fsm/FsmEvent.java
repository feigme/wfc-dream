package com.wfc.fsm;

/**
 * 事件，状态由事件触发，引起变化
 *
 * @author hui.guo
 * @since 2024/1/26 10:34 上午
 */
public interface FsmEvent<E> {

    /**
     * 返回eventId
     *
     * @return
     */
    E getEventId();

}
