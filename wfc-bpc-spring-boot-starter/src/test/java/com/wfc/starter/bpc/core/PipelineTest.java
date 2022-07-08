package com.wfc.starter.bpc.core;

import com.wfc.starter.bpc.autoconfigure.BpcConfig;
import com.wfc.starter.bpc.component.TestBeanDemoA;
import com.wfc.starter.bpc.component.TestBeanDemoB;
import com.wfc.starter.bpc.component.TestBeanDemoC;
import com.wfc.starter.bpc.component.TestBeanDemoD;
import com.wfc.starter.bpc.component.TestBeanDemoE;
import com.wfc.starter.bpc.component.TestBeanDemoF;
import com.wfc.starter.bpc.component.TestBeanDemoG;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hui.guo
 * @since 2022/6/30 4:01 下午
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = {BpcConfig.class})
@Slf4j
public class PipelineTest {

    @Autowired
    private TestBeanDemoA testBeanDemoA;
    @Autowired
    private TestBeanDemoB testBeanDemoB;
    @Autowired
    private TestBeanDemoC testBeanDemoC;
    @Autowired
    private TestBeanDemoD testBeanDemoD;
    @Autowired
    private TestBeanDemoE testBeanDemoE;
    @Autowired
    private TestBeanDemoF testBeanDemoF;
    @Autowired
    private TestBeanDemoG testBeanDemoG;

    @Test
    public void testXsd(){
//        Assert.assertEquals("测试pipeline", pipeline.getLabel());
//        Assert.assertEquals(2, pipeline.getValves().length);
//        Assert.assertEquals("测试valve", pipeline.getValves()[0].getLabel());
//        Assert.assertEquals("测试ref", pipeline.getValves()[1].getLabel());
    }

    @Test
    public void testFuncPipeline() {
        BpcPipeline pipe = BpcFuncPipelineBuilder.newInvocation("测试流程")
                .next(testBeanDemoA, "aaa1")
                .next(testBeanDemoB, "bbb1")
                .next(testBeanDemoC, "ccc1")
                .fork()
                    .and(testBeanDemoD, "ddd1")
                    .and(testBeanDemoE, "eee1")
                    .and(testBeanDemoF, "fff1")
                .join()
                .next(testBeanDemoG, "ggg1")
                .end();

        pipe.invoke(new BpcTodoContext() {});
    }
}
