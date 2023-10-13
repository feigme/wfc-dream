package com.wfc.bpc.core.func;

import com.wfc.bpc.core.BpcContext;
import com.wfc.bpc.exception.BpcPipelineException;

import java.util.Objects;

/**
 * @author hui.guo
 * @since 2022/7/6 9:24 下午
 */
@FunctionalInterface
public interface BpcRollbackFunc {

    /**
     * 执行组件
     *
     * @param ctx
     *
     * @return boolean
     */
    boolean rollback(BpcContext ctx);

    /**
     * 下一个执行的组件
     *
     * @param before
     *
     * @return
     */
    default BpcRollbackFunc andThen(BpcRollbackFunc before) {
        Objects.requireNonNull(before);
        return (x) -> {
            boolean result = before.rollback(x);
            if (!result) {
                throw new BpcPipelineException("回滚执行结果失败");
            }
            return rollback(x);
        };
    }
}
