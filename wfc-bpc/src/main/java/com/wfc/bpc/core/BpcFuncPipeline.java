package com.wfc.bpc.core;

import com.wfc.bpc.core.func.BpcValveFunc;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hui.guo
 * @since 2022/7/5 6:20 下午
 */
@Slf4j
public class BpcFuncPipeline<BpcTodoContext> implements BpcPipeline {

    private final String name;
    private final BpcValveFunc<BpcContext> valveFunc;
    private BpcContext bpcContext;

    public BpcFuncPipeline(String name, BpcValveFunc<BpcContext> valveFunc) {
        this.name = name;
        this.valveFunc = valveFunc;
    }

    @Override
    public void invoke(BpcContext bpcContext) {
        this.bpcContext = bpcContext;
        valveFunc.invoke(bpcContext);
    }
}
