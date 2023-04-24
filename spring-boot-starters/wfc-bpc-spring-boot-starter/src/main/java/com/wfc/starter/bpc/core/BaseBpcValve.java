package com.wfc.starter.bpc.core;

import com.wfc.starter.bpc.exception.BpcRollbackException;

/**
 * @author hui.guo
 * @since 2022/7/1 6:17 下午
 */
public abstract class BaseBpcValve implements BpcValve, BpcRollback {

    @Override
    public BpcContext rollback(BpcContext bpcContext) throws BpcRollbackException {
        return bpcContext;
    }
}
