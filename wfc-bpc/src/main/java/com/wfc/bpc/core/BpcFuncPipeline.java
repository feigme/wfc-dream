package com.wfc.bpc.core;

import com.google.common.base.Preconditions;
import com.wfc.bpc.core.func.BpcRollbackFunc;
import com.wfc.bpc.core.func.BpcValveFunc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author hui.guo
 * @since 2022/7/5 6:20 下午
 */
@Slf4j
public class BpcFuncPipeline implements BpcPipeline {

    private final String name;
    private BpcValveFunc valveFunc;
    private BpcRollbackFunc rollbackFunc;

    public BpcFuncPipeline(String name) {
        this.name = StringUtils.defaultString(name, "default");
    }

    public BpcFuncPipeline initValve(BpcValveFunc valveFunc, BpcRollbackFunc rollbackFunc) {
        Preconditions.checkArgument(valveFunc != null);
        this.valveFunc = valveFunc;
        this.rollbackFunc = rollbackFunc;
        return this;
    }

    @Override
    public boolean invoke(BpcContext ctx) {
        boolean result = false;
        try {
            result = valveFunc.invoke(ctx);
        } catch (Exception e) {
            log.error("valveFunc error, do rollback, ctx: {}", ctx, e);
            result = false;
        }
        if (!result && rollbackFunc != null) {
            try {
                rollbackFunc.rollback(ctx);
            } catch (Exception e) {
                log.error("rollbackFunc error, ctx: {}", ctx, e);
            }
        }
        return result;
    }

    public String getName() {
        return this.name;
    }

}
