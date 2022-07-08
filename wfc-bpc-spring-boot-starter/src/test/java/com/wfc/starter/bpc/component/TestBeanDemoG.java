package com.wfc.starter.bpc.component;

import com.wfc.starter.bpc.core.BaseBpcValve;
import com.wfc.starter.bpc.core.BpcContext;
import com.wfc.starter.bpc.exception.BpcRollbackException;
import com.wfc.starter.bpc.exception.BpcValveException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hui.guo
 * @since 2022/7/1 3:39 下午
 */
@Data
@Slf4j
public class TestBeanDemoG extends BaseBpcValve {

    private String name = "gggggg";

    @Override
    public BpcContext invoke(BpcContext context) throws BpcValveException {
        log.info(">>>>>>>>>>>{}", name);
//        return context;
        throw new BpcValveException("dkk--sk");
    }

    @Override
    public BpcContext rollback(BpcContext context) throws BpcRollbackException {
        log.info("------rollback-----:{}", name);
        return context;
    }
}
