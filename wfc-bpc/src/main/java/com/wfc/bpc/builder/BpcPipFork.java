package com.wfc.bpc.builder;

import com.wfc.bpc.core.BpcValve;

/**
 * @author hui.guo
 * @since 2023/5/26 5:34 下午
 */
public interface BpcPipFork {

    /**
     * 添加并行的valve
     *
     * @param bpcValve
     * @param valveName
     *
     * @return
     */
    BpcPipForkAnd and(BpcValve bpcValve, String valveName);

    /**
     * 合并成一个valve
     *
     * @return
     */
    BpcPipNext join();

}
