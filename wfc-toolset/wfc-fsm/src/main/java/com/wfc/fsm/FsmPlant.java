package com.wfc.fsm;

import java.util.Map;

/**
 * @author hui.guo
 * @since 2024/1/30 5:42 下午
 */
public interface FsmPlant<S, E, C> extends FsmMachine<S, E, C> {

    Map<S, FsmState<S, E, C>> getStateMap();

}
