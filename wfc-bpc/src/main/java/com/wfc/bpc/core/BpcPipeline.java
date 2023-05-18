package com.wfc.bpc.core;

import com.wfc.bpc.exception.BpcPipelineException;

/**
 * @author hui.guo
 * @since 2022/7/1 5:37 下午
 */
public interface BpcPipeline<T extends BpcContext> {

    /**
     * pipeline执行
     *
     * @param t
     *
     * @throws BpcPipelineException
     */
    void invoke(T t) throws BpcPipelineException;
}
