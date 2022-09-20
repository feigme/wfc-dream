package com.wfc.starter.bpc.autoconfigure;

import com.wfc.starter.bpc.component.TestBeanDemoA;
import com.wfc.starter.bpc.component.TestBeanDemoB;
import com.wfc.starter.bpc.component.TestBeanDemoC;
import com.wfc.starter.bpc.component.TestBeanDemoD;
import com.wfc.starter.bpc.component.TestBeanDemoE;
import com.wfc.starter.bpc.component.TestBeanDemoF;
import com.wfc.starter.bpc.component.TestBeanDemoG;
import com.wfc.starter.bpc.core.BpcFuncPipelineBuilder;
import com.wfc.starter.bpc.core.BpcPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author hui.guo
 * @since 2022/6/30 4:15 下午
 */
@Configuration
@ImportResource(locations = {"classpath:application-bpc.xml"})
public class BpcConfig {

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

    @Bean
    public BpcPipeline getFuncPipeline(){
        return BpcFuncPipelineBuilder.newInvocation("测试流程")
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
    }


}
