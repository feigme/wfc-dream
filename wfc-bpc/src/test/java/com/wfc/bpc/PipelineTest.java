package com.wfc.bpc;

import com.wfc.bpc.component.TestInvokeSuccA;
import com.wfc.bpc.component.TestInvokeFailB;
import com.wfc.bpc.component.TestInvokeSuccAndRollbackFailC;
import com.wfc.bpc.component.TestBeanWithExceptionDemoD;
import com.wfc.bpc.component.TestTimeOutE;
import com.wfc.bpc.builder.BpcFuncPipBuilder;
import com.wfc.bpc.core.BpcPipeline;
import com.wfc.bpc.core.BpcTodoContext;
import org.junit.Test;

/**
 * @author hui.guo
 * @since 2023/5/25 3:59 下午
 */
public class PipelineTest {

    BpcPipeline pipeline1 = BpcFuncPipBuilder.inst("测试流程")
            .next(new TestInvokeSuccA(), "a")
                .next(new TestInvokeFailB(), "b")
                .next(new TestInvokeSuccAndRollbackFailC(), "c")
                .fork()
                .and(new TestInvokeSuccA(), "da1")
                    .next(new TestInvokeSuccA(), "da2")
                .and(new TestInvokeSuccA(), "db1")
                .join()
                .next(new TestInvokeSuccA(), "e1")
                .builder();

    @Test
    public void test_测试pipeline() {
        pipeline1.invoke(new BpcTodoContext());
    }

}
