package com.wfc.starter.bpc.core;

import com.wfc.starter.bpc.autoconfigure.BpcConfig;
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
    private BpcPipeline bpcPipeline;

    @Test
    public void testFuncPipeline() {
        bpcPipeline.invoke(new BpcTodoContext() {
        });
    }
}
