package com.wfc.bpc.component;


import com.wfc.bpc.core.BaseBpcValve;
import com.wfc.bpc.core.BpcContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hui.guo
 * @since 2022/7/1 3:39 下午
 */
@Data
@Slf4j
public class TestInvokeRepeatSuccA extends BaseBpcValve {

    private String name;

    public TestInvokeRepeatSuccA(String name) {
        this.name = name;
    }

    @Override
    public boolean invoke(BpcContext context) {
        for (int i = 0; i < 3; i++) {
            try {
                log.info("repeat name: {}, i: {}", name, i);
                Thread.sleep(3);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

}
