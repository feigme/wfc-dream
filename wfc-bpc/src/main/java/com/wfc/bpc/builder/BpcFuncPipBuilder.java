package com.wfc.bpc.builder;

import com.google.common.base.Preconditions;
import com.wfc.bpc.core.BpcFuncPipeline;
import com.wfc.bpc.core.BpcPipeline;
import com.wfc.bpc.core.BpcRollback;
import com.wfc.bpc.core.BpcValve;
import com.wfc.bpc.core.func.BpcRollbackFunc;
import com.wfc.bpc.core.func.BpcValveFunc;
import com.wfc.bpc.exception.BpcValveException;
import lombok.extern.slf4j.Slf4j;

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
public class BpcFuncPipBuilder implements BpcPipNext {

    private BpcFuncPipBuilder builder = this;

    private BpcValveFunc valveFunc;
    private BpcRollbackFunc rollbackFunc;

    private final BpcFuncPipeline pipeline;

    private BpcFuncPipBuilder(String pipelineName) {
        pipeline = new BpcFuncPipeline(pipelineName);

        // first valve
        valveFunc = (ctx) -> {
            ctx.getAttribute("pipelineStartTime", (x) -> System.currentTimeMillis());
            log.info("[Pipeline-func] 执行pipeline：{}", pipelineName);
            return true;
        };

        // first rollback
        rollbackFunc = (ctx) -> {
            log.info("[Pipeline-func] 执行pipeline-rollback-end, spent: {}ms", System.currentTimeMillis() - (long) ctx.getAttribute("RollbackStartTime"));
            return true;
        };
    }

    /**
     * 初始化新的pipeline
     *
     * @param pipelineName
     *
     * @return
     */
    public static final BpcPipNext inst(String pipelineName) {
        return new BpcFuncPipBuilder(pipelineName);
    }

    @Override
    public BpcPipNext next(BpcValve valve, String valveName) {
        valveFunc = valveFunc.andThen(this.logAroundFunc(valve, valveName));
        return this;
    }

    @Override
    public BpcPipFork fork() {
        return new BpcFuncPipForkBuilder();
    }

    @Override
    public BpcPipeline builder() {
        // last valve
        valveFunc.andThen((ctx) -> {
            ctx.setFinished(true);
            log.info("[Pipeline-func] 执行pipeline-end：{}, spent: {}ms", this.pipeline.getName(), System.currentTimeMillis() - (long) ctx.getAttribute("pipelineStartTime"));
            return true;
        });

        // log pipeline的流程
//        StringBuilder sb = new StringBuilder(bpcFuncPipeline.getName()).append("\n");
//        for (int i = 0; i < valveFuncList.size(); i++) {
//            if (i < valveFuncList.size() - 1) {
//                sb.append("├── ");
//            } else {
//                sb.append("└── ");
//            }
//
//            sb.append(valveFuncList.get(i).getLeft()).append("\n");
//        }
//        log.info("bpc pipeline: \n{}", sb);

        return this.pipeline.initValve(valveFunc, rollbackFunc);
    }


    private BpcValveFunc logAroundFunc(BpcValve bpcValve, String valveName) {
        Preconditions.checkArgument(bpcValve != null);

        return (ctx) -> {
            long s = System.currentTimeMillis();
            try {
                bpcValve.invoke(ctx);

                if (!ctx.isBroken() && bpcValve instanceof BpcRollback) {
                    rollbackFunc = rollbackFunc.andThen(this.logAroundRollback((BpcRollback) bpcValve, valveName));
                }

                log.info("[Pipeline-func] valve: {}, spent: {}ms", valveName, System.currentTimeMillis() - s);
                return true;
            } catch (BpcValveException valveException) {
                // broke
                log.error("[Pipeline-func] 执行pipeline 异常", valveException);
                ctx.setMessage(valveException.getMessage());
                ctx.setBroken(true);
                return false;
            } catch (Exception e) {
                // broke
                log.error("[Pipeline-func] valve 异常", e);
                ctx.setMessage(e.getMessage());
                ctx.setBroken(true);
                return false;
            }
        };
    }

    private BpcRollbackFunc logAroundRollback(BpcRollback bpcRollback, String valveName) {
        return (ctx) -> {
            long rs = System.currentTimeMillis();
            bpcRollback.rollback(ctx);
            log.info("[Pipeline-func] rollback valve: {}, spent: {}ms", valveName, System.currentTimeMillis() - rs);
            return true;
        };
    }

    class BpcFuncPipForkBuilder implements BpcPipFork {

        List<BpcFuncPipForkParBuilder> forkList = new ArrayList<>();

        @Override
        public BpcPipForkAnd and(BpcValve bpcValve, String valveName) {
            BpcFuncPipForkParBuilder forkAndBuilder = new BpcFuncPipForkParBuilder(this, bpcValve, valveName);
            forkList.add(forkAndBuilder);
            return forkAndBuilder;
        }

        @Override
        public BpcPipNext join() {
            return this.concurrent(forkList);
        }

        /**
         * 并行执行valve
         *
         * @param forkList
         *
         * @return
         */
        private BpcPipNext concurrent(List<BpcFuncPipForkParBuilder> forkList) {
            Objects.requireNonNull(forkList);

            BpcValveFunc concurrentFunc = (ctx) -> {
                List<CompletableFuture<Void>> futureList = new ArrayList<>(forkList.size());
                for (BpcFuncPipForkParBuilder forkParBuilder : forkList) {
                    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                        long s = System.currentTimeMillis();
                        forkParBuilder.forkAndValveFunc.invoke(ctx);
                        log.info("[Pipeline-func] Parallel Valve: {}, spent: {}ms", forkParBuilder.forkAndValveName, System.currentTimeMillis() - s);
                    });
                    futureList.add(completableFuture);
                }
                CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
                try {
                    future.get(1000L, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    log.error("[Pipeline-func] 执行concurrent 超时");
                    return false;
                }

                return true;
            };

            valveFunc = valveFunc.andThen(concurrentFunc);
            return builder;
        }

        class BpcFuncPipForkParBuilder implements BpcPipForkAnd {
            BpcValveFunc forkAndValveFunc;
            String forkAndValveName;
            BpcFuncPipForkBuilder forkBuilder;

            BpcFuncPipForkParBuilder(BpcFuncPipForkBuilder forkBuilder, BpcValve valve, String valveName) {
                this.forkAndValveName = valveName;
                this.forkBuilder = forkBuilder;
                forkAndValveFunc = logAroundFunc(valve, valveName);
            }

            @Override
            public BpcPipForkAnd and(BpcValve bpcValve, String valveName) {
                return forkBuilder.and(bpcValve, valveName);
            }

            @Override
            public BpcPipForkAnd next(BpcValve bpcValve, String valveName) {
                forkAndValveFunc = forkAndValveFunc.andThen(logAroundFunc(bpcValve, valveName));
                return this;
            }

            @Override
            public BpcPipNext join() {
                return forkBuilder.join();
            }
        }
    }
}
