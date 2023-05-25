package com.wfc.bpc.core;

import com.wfc.bpc.core.func.BpcRollbackFunc;
import com.wfc.bpc.core.func.BpcValveFunc;
import com.wfc.bpc.exception.BpcValveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author hui.guo
 * @since 2022/7/5 8:57 下午
 */
@Slf4j
public class BpcFuncPipelineBuilder {

    private String pipelineName;

    private BpcValveFunc<BpcContext> newPipeline = (ctx) -> {
        ctx.getAttribute("pipelineStartTime", (x) -> System.currentTimeMillis());
        log.info("[Pipeline-func] 执行pipeline：{}", pipelineName);
        return ctx;
    };

    private BpcRollbackFunc<BpcContext> rollbackPipeline = (ctx) -> {
        log.info("[Pipeline-func] 执行pipeline-rollback-end, spent: {}ms", System.currentTimeMillis() - (long) ctx.getAttribute("RollbackStartTime"));
        return ctx;
    };

    private BpcFuncPipelineBuilder(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    /**
     * 初始化新的pipeline
     *
     * @param pipelineName
     *
     * @return
     */
    public static final BpcFuncPipelineBuilder newInvocation(String pipelineName) {
        return new BpcFuncPipelineBuilder(pipelineName);
    }

    /**
     * 串行的下一个valve
     *
     * @param valve
     *
     * @return
     */
    public BpcFuncPipelineBuilder next(BpcValve valve, String valveName) {
        Objects.requireNonNull(valve);

        // 包装统计日志
        BpcValveFunc<BpcContext> logAroundFunc = (ctx) -> {
            if (ctx.isBroken()) {
                return ctx;
            }

            long s = System.currentTimeMillis();
            try {
                valve.invoke(ctx);

                if (!ctx.isBroken() && valve instanceof BpcRollback) {
                    rollbackPipeline = rollbackPipeline.andThen(this.logAroundRollback(valve, valveName));
                }

                log.info("[Pipeline-func] valve: {}, spent: {}ms", valveName, System.currentTimeMillis() - s);
                return ctx;
            } catch (BpcValveException valveException) {
                // broke
                log.error("[Pipeline-func] 执行pipeline 异常", valveException);
                ctx.setMessage(valveException.getMessage());
                this.broke(ctx);
                return ctx;
            } catch (Exception e) {
                // broke
                log.error("[Pipeline-func] valve 异常", e);
                ctx.setMessage(e.getMessage());
                this.broke(ctx);
                return ctx;
            }
        };
        newPipeline = newPipeline.andThen(logAroundFunc);
        return this;
    }

    public BpcForkJoin fork() {
        return new BpcForkJoin(this);
    }

    /**
     * 并行执行valve
     *
     * @param forkList
     *
     * @return
     */
    private BpcFuncPipelineBuilder concurrent(List<Pair<String, BpcValve>> forkList) {
        Objects.requireNonNull(forkList);

        BpcValveFunc<BpcContext> concurrentFunc = (ctx) -> {
            List<CompletableFuture<Void>> futureList = new ArrayList<>(forkList.size());
            for (Pair<String, BpcValve> pair : forkList) {
                BpcValve valve = pair.getRight();
                String valveName = pair.getLeft();
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                    long s = System.currentTimeMillis();
                    valve.invoke(ctx);

                    if (valve instanceof BpcRollback) {
                        rollbackPipeline = rollbackPipeline.andThen(this.logAroundRollback(valve, valveName));
                    }
                    log.info("[Pipeline-func] concurrent value: {}, spent: {}ms", valveName, System.currentTimeMillis() - s);
                });
                futureList.add(completableFuture);
            }
            CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
            try {
                future.get(1000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                log.info("[Pipeline-func] 执行concurrent 超时");
                this.broke(ctx);
            }

            return ctx;
        };

        newPipeline = newPipeline.andThen(concurrentFunc);
        return this;
    }

    private BpcRollbackFunc<BpcContext> logAroundRollback(BpcValve valve, String valveName) {
        return (ctx) -> {
            long rs = System.currentTimeMillis();
            ((BpcRollback) valve).rollback(ctx);
            log.info("[Pipeline-func] rollback value: {}, spent: {}ms", valveName, System.currentTimeMillis() - rs);
            return ctx;
        };
    }

    private void broke(BpcContext ctx) {
        log.info("[Pipeline-func] 执行pipeline-rollback: {}", pipelineName);
        ctx.setBroken(true);
        ctx.putAttribute("RollbackStartTime", System.currentTimeMillis());
        rollbackPipeline.rollback(ctx);
    }

    public BpcPipeline end() {
        return new BpcFuncPipeline<BpcContext>(pipelineName, newPipeline.andThen((ctx) -> {
            if (ctx.isBroken()) {
                return ctx;
            }
            ctx.setFinished(true);
            log.info("[Pipeline-func] 执行pipeline-end：{}, spent: {}ms", pipelineName, System.currentTimeMillis() - (long) ctx.getAttribute("pipelineStartTime"));
            return ctx;
        }));
    }

    public class BpcForkJoin {

        BpcFuncPipelineBuilder builder;
        List<Pair<String, BpcValve>> forkList = new ArrayList<>();

        BpcForkJoin(BpcFuncPipelineBuilder builder) {
            this.builder = builder;
        }

        public BpcForkJoin and(BpcValve valve, String valveName) {
            forkList.add(Pair.of(valveName, valve));
            return this;
        }

        public BpcFuncPipelineBuilder join() {
            return builder.concurrent(forkList);
        }
    }
}
