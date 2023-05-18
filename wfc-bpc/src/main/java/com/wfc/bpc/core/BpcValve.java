package com.wfc.bpc.core;

import com.wfc.bpc.exception.BpcValveException;

/**
 * @author hui.guo
 * @since 2022/7/1 5:26 下午
 */
public interface BpcValve {

    /**
     * 执行组件
     *
     * @param context
     *
     * @return
     * @throws BpcValveException
     */
    BpcContext invoke(BpcContext context) throws BpcValveException;

}
