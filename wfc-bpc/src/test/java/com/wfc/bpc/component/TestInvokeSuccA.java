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
public class TestInvokeSuccA extends BaseBpcValve {

    @Override
    public boolean invoke(BpcContext context) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
        return true;
    }

}
