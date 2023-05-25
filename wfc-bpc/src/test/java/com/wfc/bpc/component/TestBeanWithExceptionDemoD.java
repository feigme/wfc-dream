package com.wfc.bpc.component;

import com.wfc.bpc.core.BaseBpcValve;
import com.wfc.bpc.core.BpcContext;
import com.wfc.bpc.exception.BpcRollbackException;
import com.wfc.bpc.exception.BpcValveException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hui.guo
 * @since 2022/7/1 3:39 下午
 */
@Data
@Slf4j
public class TestBeanWithExceptionDemoD extends BaseBpcValve {

    private String name = "dddddd";

    @Override
    public BpcContext invoke(BpcContext context) throws BpcValveException {
        for (int i = 0; i < 3; i++) {
            log.info(">>>>>>>>>>>{} {}", name, i);
            if (i == 2 ) {
                throw new BpcValveException("出现异常");
            }
        }
        return context;
    }

    @Override
    public BpcContext rollback(BpcContext context) throws BpcRollbackException {
        log.info("------rollback-----:{}", name);
        return context;
    }
}
