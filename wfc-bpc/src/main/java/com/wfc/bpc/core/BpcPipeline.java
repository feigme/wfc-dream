package com.wfc.bpc.core;

/**
 * @author hui.guo
 * @since 2022/7/1 5:37 下午
 */
public interface BpcPipeline {

    /**
     * pipeline执行
     *
     * @param ctx
     *
     * @return boolean
     */
    boolean invoke(BpcContext ctx);
}
