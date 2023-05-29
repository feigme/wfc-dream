package com.wfc.bpc.builder;

import com.wfc.bpc.core.BpcPipeline;
import com.wfc.bpc.core.BpcValve;

/**
 * @author hui.guo
 * @since 2023/5/26 5:29 下午
 */
public interface BpcPipNext {

    /**
     * 串行的下一个valve
     *
     * @param bpcValve
     * @param valveName
     * @return
     */
    BpcPipNext next(BpcValve bpcValve, String valveName);

    /**
     * 并行处理valve
     * @return
     */
    BpcPipFork fork();

    BpcPipeline builder();
}
