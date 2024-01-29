package com.wfc.fsm.builder;

/**
 * @author hui.guo
 * @since 2022/7/13 11:14 下午
 */
public class WfcFsmMachineBuilderFactory {

    public static <S, E, C> FsmMachineBuilder<S, E, C> create(String machineId) {
        return new WfcFsmMachineBuilder<>(machineId);
    }

}
