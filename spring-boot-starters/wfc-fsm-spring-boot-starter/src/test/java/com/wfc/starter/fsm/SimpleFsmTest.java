package com.wfc.starter.fsm;

import com.wfc.starter.fsm.builder.FsmBuilder;
import com.wfc.starter.fsm.builder.impl.FsmBuilderImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author hui.guo
 * @since 2022/7/13 11:40 下午
 */
public class SimpleFsmTest {

    enum DemoState {
        STATE1, STATE2, STATE3, STATE4
    }

    enum DemoEvent {
        EVENT1, EVENT2, EVENT3, EVENT4
    }

    static class Context {
    }

    @Test
    public void testExternalNormal() {
        FsmBuilder<DemoState, DemoEvent, Context> builder = new FsmBuilderImpl<>();
        builder.externalTransition()
                .from(DemoState.STATE1)
                .to(DemoState.STATE2)
                .on(DemoEvent.EVENT1)
                .when((c) -> true)
                .action((from, to, event, ctx) -> System.out.println("111111"));

        FsmMachine<DemoState, DemoEvent, Context> fsm1 = builder.build("TestFsm1");
        DemoState target = fsm1.fireEvent(DemoState.STATE1, DemoEvent.EVENT1, new Context());
        Assert.assertNotNull(target);
        Assert.assertEquals(DemoState.STATE2, target);
    }
}
