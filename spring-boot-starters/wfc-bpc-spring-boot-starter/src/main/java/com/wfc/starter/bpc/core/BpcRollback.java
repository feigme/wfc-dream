package com.wfc.starter.bpc.core;

import com.wfc.starter.bpc.exception.BpcRollbackException;

/**
 * @author hui.guo
 * @since 2022/7/6 9:29 下午
 */
public interface BpcRollback {

    /**
     * 执行组件
     *
     * @param context
     *
     * @return
     * @throws BpcRollbackException
     */
    BpcContext rollback(BpcContext context) throws BpcRollbackException;


}
