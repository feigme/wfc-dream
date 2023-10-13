package com.wfc.bpc;

import com.wfc.bpc.component.TestBeanWithExceptionDemoD;
import com.wfc.bpc.component.TestInvokeFailB;
import com.wfc.bpc.component.TestInvokeRepeatSuccA;
import com.wfc.bpc.component.TestInvokeSuccA;
import com.wfc.bpc.core.BpcFuncPipeline;
import com.wfc.bpc.core.BpcPipeline;
import com.wfc.bpc.core.BpcTodoContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author hui.guo
 * @since 2023/5/25 3:59 下午
 */
@DisplayName("测试类，观察日志")
public class PipelineTest {

    @Test
    public void test_简单串行pipeline() {
        BpcPipeline bpcPipeline = BpcFuncPipeline.build("1层串行pipeline")
                .next(new TestInvokeSuccA(), "a")
                .next(new TestInvokeRepeatSuccA("b"), "b")
                .next(new TestInvokeSuccA(), "c")
                .builder();
        bpcPipeline.invoke(new BpcTodoContext());
    }

    @Test
    public void test_串行并行组合pipeline() {
        BpcPipeline bpcPipeline = BpcFuncPipeline.build("测试流程")
                .next(new TestInvokeSuccA(), "a")
                .next(new TestInvokeFailB(), "b")
                .fork()
                .and(new TestInvokeRepeatSuccA("c1-a"), "c1-a")
                .and(new TestInvokeSuccA(), "c2-a")
                .join()
                .next(new TestInvokeSuccA(), "d")
                .builder();

        bpcPipeline.invoke(new BpcTodoContext());
    }

    @Test
    public void test_并行后串行pipeline() {
        BpcPipeline bpcPipeline = BpcFuncPipeline.build("测试流程")
                .next(new TestInvokeSuccA(), "a")
                .next(new TestInvokeFailB(), "b")
                .fork()
                .and(new TestInvokeSuccA(), "c1-a")
                .next(new TestInvokeSuccA(), "c1-b")
                .and(new TestInvokeSuccA(), "c2-a")
                .join()
                .next(new TestInvokeSuccA(), "d")
                .builder();

        bpcPipeline.invoke(new BpcTodoContext());
    }


    @Test
    public void test_回滚pipeline() {
        BpcPipeline bpcPipeline = BpcFuncPipeline.build("测试流程")
                .next(new TestInvokeSuccA(), "a")
                .next(new TestInvokeFailB(), "b")
                .fork()
                .and(new TestInvokeSuccA(), "c1-a")
                .next(new TestBeanWithExceptionDemoD(), "c1-b")
                .and(new TestInvokeSuccA(), "c2-a")
                .join()
                .next(new TestInvokeSuccA(), "d")
                .builder();

        bpcPipeline.invoke(new BpcTodoContext());
    }

}
