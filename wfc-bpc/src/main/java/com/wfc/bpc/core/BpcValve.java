package com.wfc.bpc.core;

/**
 * @author hui.guo
 * @since 2022/7/1 5:26 下午
 */
public interface BpcValve {

    /**
     * 执行组件
     *
     * @param ctx
     *
     * @return boolean
     */
    boolean invoke(BpcContext ctx);

}
