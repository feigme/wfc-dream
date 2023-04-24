package com.wfc.starter.fsm;

import com.wfc.starter.fsm.exception.FsmException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hui.guo
 * @since 2022/7/13 11:14 下午
 */
public class FsmFactory {

    static Map<String, FsmMachine> fsmMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> void register(FsmMachine<S, E, C> fsmMachine) {
        String machineId = fsmMachine.getMachineId();
        if (fsmMachineMap.containsKey(machineId)) {
            throw new FsmException(String.format("The fsm has existed, key: %s", machineId));
        }
        fsmMachineMap.put(machineId, fsmMachine);
    }

    public static <S, E, C> FsmMachine<S, E, C> get(String machineId){
        if (!fsmMachineMap.containsKey(machineId)) {
            throw new FsmException(String.format("The fsm is not existed, key: %s", machineId));
        }
        return fsmMachineMap.get(machineId);
    }
}
