package com.wfc.bpc.core;

/**
 * @author hui.guo
 * @since 2022/7/6 9:29 下午
 */
public interface BpcRollback {

    /**
     * 回滚组件
     *
     * @param ctx
     *
     * @return boolean
     */
    boolean rollback(BpcContext ctx);

}
