package com.wfc.bpc;

import com.wfc.bpc.component.TestBeanDemoA;
import com.wfc.bpc.component.TestBeanDemoB;
import com.wfc.bpc.component.TestBeanDemoC;
import com.wfc.bpc.component.TestBeanWithExceptionDemoD;
import com.wfc.bpc.component.TestBeanDemoE;
import com.wfc.bpc.component.TestBeanDemoF;
import com.wfc.bpc.component.TestBeanDemoG;
import com.wfc.bpc.core.BpcFuncPipelineBuilder;
import com.wfc.bpc.core.BpcPipeline;
import com.wfc.bpc.core.BpcTodoContext;
import org.junit.Test;

/**
 * @author hui.guo
 * @since 2023/5/25 3:59 下午
 */
public class PipelineTest {

    BpcPipeline pipeline1 = BpcFuncPipelineBuilder.newInvocation("测试流程")
            .next(new TestBeanDemoA(), "aaa1")
                .next(new TestBeanDemoB(), "bbb1")
                .next(new TestBeanDemoC(), "ccc1")
                .fork()
                .and(new TestBeanWithExceptionDemoD(), "ddd1")
                .and(new TestBeanDemoE(), "eee1")
                .and(new TestBeanDemoF(), "fff1")
                .join()
                .next(new TestBeanDemoG(), "ggg1")
                .end();

    @Test
    public void test_测试pipeline() {
        pipeline1.invoke(new BpcTodoContext() {});
    }

}
