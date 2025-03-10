package com.wfc.fsm;

import com.wfc.fsm.builder.FsmMachineBuilder;
import com.wfc.fsm.builder.WfcFsmMachineBuilderFactory;
import com.wfc.fsm.exception.FsmException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author hui.guo
 * @since 2024/1/25 6:04 下午
 */
public class FsmMachineTest {

    enum TradeStatus {
        INIT, PAYING, SUCCESS, CLOSE
    }

    enum TradeEvent {
        FULL_PAY_SUCESS, PAY, CLOSE
    }

    static class Context {
        String operator = "aaa";
    }

    static {
        FsmMachineBuilder<TradeStatus, TradeEvent, Context> fsmMachineBuilder = WfcFsmMachineBuilderFactory.create("test1");
        fsmMachineBuilder.externalTransition()
                .from(TradeStatus.INIT).to(TradeStatus.SUCCESS)
                .on(TradeEvent.FULL_PAY_SUCESS)
                .action((fromStateId, toStateId, event, ctx) -> System.out.println("===>支付成功"));

        fsmMachineBuilder.externalTransition()
                .from(TradeStatus.INIT).to(TradeStatus.PAYING)
                .on(TradeEvent.PAY)
                .action((fromStateId, toStateId, event, ctx) -> System.out.println("===>支付"));

        fsmMachineBuilder.externalTransition()
                .from(TradeStatus.INIT).to(TradeStatus.CLOSE)
                .on(TradeEvent.CLOSE)
                .action((fromStateId, toStateId, event, ctx) -> System.out.println("===>关闭"));

        fsmMachineBuilder.externalTransition()
                .from(TradeStatus.PAYING).to(TradeStatus.SUCCESS)
                .on(TradeEvent.FULL_PAY_SUCESS)
                .action((fromStateId, toStateId, event, ctx) -> System.out.println("===>支付成功"));

        fsmMachineBuilder.externalTransition()
                .from(TradeStatus.PAYING).to(TradeStatus.CLOSE)
                .on(TradeEvent.CLOSE)
                .action((fromStateId, toStateId, event, ctx) -> System.out.println("===>关闭"));

        fsmMachineBuilder.build();
    }

    @Test
    public void test_simple() {
        FsmMachine<TradeStatus, TradeEvent, Context> fsmMachine = WfcFsmMachineFactory.get("test1");
        TradeStatus target = fsmMachine.fireEvent(TradeStatus.INIT, TradeEvent.PAY, new Context());
        Assertions.assertEquals(TradeStatus.PAYING, target);
    }

    @Test
    public void test_fail() {
        FsmMachine<TradeStatus, TradeEvent, Context> fsmMachine = WfcFsmMachineFactory.get("test1");
        Assertions.assertThrows(FsmException.class, () -> fsmMachine.fireEvent(TradeStatus.CLOSE, TradeEvent.CLOSE, new Context()));
    }

    @Test
    public void test_plantUml() {
        FsmPlant fsmMachine = (FsmPlant) WfcFsmMachineFactory.get("test1");
        String uml = FsmPlantUmlHelper.plantUml(fsmMachine);
        System.out.println(uml);
    }


}
