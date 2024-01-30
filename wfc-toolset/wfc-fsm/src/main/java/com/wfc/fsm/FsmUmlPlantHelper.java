package com.wfc.fsm;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.LF;

/**
 * @author hui.guo
 * @since 2024/1/30 5:23 下午
 */
public class FsmUmlPlantHelper {

    public static <S, E, C> String showPlantUml(FsmPlant<S, E, C> fsmMachine) {
        Map<S, FsmState<S, E, C>> stateMap = fsmMachine.getStateMap();
        StringBuilder sb = new StringBuilder("@startuml").append(LF);
        sb.append("state ").append(fsmMachine.getMachineId()).append(" {").append(LF);
        stateMap.values().stream()
                .flatMap(s -> s.getAllTransition().stream())
                .forEach(fsmTransition -> {
                    sb.append(fsmTransition.getSource().getStateId())
                            .append(" --> ")
                            .append(fsmTransition.getTarget().getStateId())
                            .append(" : ")
                            .append(fsmTransition.getEvent())
                            .append(LF);
                });
        sb.append("}").append(LF);
        sb.append("@enduml");
        return sb.toString();
    }

}
