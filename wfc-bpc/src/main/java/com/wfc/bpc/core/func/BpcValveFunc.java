package com.wfc.bpc.core.func;

import com.wfc.bpc.core.BpcContext;
import com.wfc.bpc.exception.BpcPipelineException;

import java.util.Objects;

/**
 * @author hui.guo
 * @since 2022/7/5 5:10 下午
 */
@FunctionalInterface
public interface BpcValveFunc {

    /**
     * 执行组件
     *
     * @param ctx
     *
     * @return
     */
    boolean invoke(BpcContext ctx);

    /**
     * 下一个执行的组件
     *
     * @param after
     *
     * @return
     */
    default BpcValveFunc andThen(BpcValveFunc after) {
        Objects.requireNonNull(after);
        return (x) -> {
            boolean result = invoke(x);
            if (!result) {
                throw new BpcPipelineException("执行结果失败");
            }
            return after.invoke(x);
        };
    }
}
