package com.wfc.bpc.builder;

import com.wfc.bpc.core.BpcValve;

/**
 * @author hui.guo
 * @since 2023/5/26 5:34 下午
 */
public interface BpcPipForkAnd extends BpcPipFork {

    /**
     * 添加串行的valve
     *
     * @param bpcValve
     * @param valveName
     *
     * @return
     */
    BpcPipForkAnd next(BpcValve bpcValve, String valveName);
}
