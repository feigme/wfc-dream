package com.wfc.fsm;

import com.wfc.fsm.exception.FsmException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hui.guo
 * @since 2024/1/26 4:35 下午
 */
public class WfcFsmMachineFactory {

    static Map<String, FsmMachine> fsmMachineMap = new ConcurrentHashMap<>();

    public static <S, E, C> FsmMachine<S, E, C> get(String machineId) {
        if (!isExisted(machineId)) {
            throw new FsmException(String.format("The FSM does not exist, key: %s", machineId));
        }
        return fsmMachineMap.get(machineId);
    }

    public static boolean isExisted(String machineId) {
        return fsmMachineMap.containsKey(machineId);
    }

    public static <S, E, C> void register(FsmMachine<S, E, C> fsmMachine) {
        String machineId = fsmMachine.getMachineId();
        if (isExisted(machineId)) {
            throw new FsmException(String.format("The FSM has existed, key: %s", machineId));
        }
        fsmMachineMap.put(machineId, fsmMachine);
    }

}
