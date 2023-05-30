package com.wfc.bpc.core;

import com.google.common.base.Preconditions;
import com.wfc.bpc.builder.BpcPipFork;
import com.wfc.bpc.builder.BpcPipForkAnd;
import com.wfc.bpc.builder.BpcPipNext;
import com.wfc.bpc.builder.model.BpcDoublyLinkedModel;
import com.wfc.bpc.builder.model.BpcModel;
import com.wfc.bpc.builder.model.ParallelValveModel;
import com.wfc.bpc.builder.model.SerialValveModel;
import com.wfc.bpc.core.func.BpcRollbackFunc;
import com.wfc.bpc.core.func.BpcValveFunc;
import com.wfc.bpc.exception.BpcPipelineException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

    public BpcFuncPipeline initValve(BpcValveFunc valveFunc) {
        Preconditions.checkArgument(valveFunc != null);
        this.valveFunc = valveFunc;
        return this;
    }

    @Override
    public boolean invoke(BpcContext ctx) {
        boolean result = false;
        log.info("开始执行pipeline: {}, instId: {}", name, ctx.getId());
        long a = System.currentTimeMillis();
        try {
            result = valveFunc.invoke(ctx);
        } catch (Exception e) {
            result = false;
        }
        if (!result && rollbackFunc != null) {
            log.info("pipeline执行异常, do rollback, ctx: {}", ctx);
            try {
                rollbackFunc.rollback(ctx);
            } catch (Exception e) {
                log.error("pipeline回滚异常, ctx: {}", ctx, e);
            }
        }
        log.info("执行pipeline-end：{}, instId: {}, spent: {}ms", name, ctx.getId(), System.currentTimeMillis() - a);
        return result;
    }

    public String getName() {
        return this.name;
    }

    public final static BpcPipNext build(String pipelineName) {
        return new FuncPipBuilder(pipelineName);
    }

    static class FuncPipBuilder implements BpcPipNext {

        private FuncPipBuilder builder = this;

        private BpcDoublyLinkedModel linkedBpcModel;

        private BpcFuncPipeline pipeline;

        public FuncPipBuilder(String pipelineName) {
            pipeline = new BpcFuncPipeline(pipelineName);

            linkedBpcModel = new BpcDoublyLinkedModel(pipelineName, new SerialValveModel("Start", (ctx) -> {
                return true;
            }));

            // first rollback
            pipeline.rollbackFunc = (ctx) -> {
                return true;
            };
        }

        @Override
        public BpcPipNext next(BpcValve valve, String valveName) {
            linkedBpcModel.append(new SerialValveModel(valveName, valve));
            return this;
        }

        @Override
        public BpcPipFork fork() {
            FuncPipBuilder.BpcFuncPipForkBuilder forkBuilder = new FuncPipBuilder.BpcFuncPipForkBuilder();

            linkedBpcModel.append(forkBuilder.getParallelValveModel());

            return forkBuilder;
        }

        @Override
        public BpcPipeline builder() {
            linkedBpcModel.append(new SerialValveModel("End", (ctx) -> {
                ctx.setFinished(true);
                return true;
            }));

            log.info("pipeline: {} \n{}", pipeline.getName(), linkedBpcModel.print());

            return this.pipeline.initValve(builderFunc(linkedBpcModel));

        }

        private BpcValveFunc builderFunc(BpcDoublyLinkedModel linkedBpcModel) {
            BpcModel current = linkedBpcModel.getHead();
            BpcValveFunc valveFunc = null;
            while (current != null) {
                if (current instanceof SerialValveModel) {
                    if (valveFunc == null) {
                        valveFunc = this.logAroundFunc(((SerialValveModel) current).getValve(), current.getName());
                    } else {
                        valveFunc = valveFunc.andThen(this.logAroundFunc(((SerialValveModel) current).getValve(), current.getName()));
                    }
                } else {
                    List<BpcValveFunc> parList = new ArrayList<>();
                    for (BpcDoublyLinkedModel bpcDoublyLinkedModel : ((ParallelValveModel) current).getValveList()) {
                        parList.add(this.builderFunc(bpcDoublyLinkedModel));
                    }

                    valveFunc = valveFunc.andThen(this.concurrent(parList));
                }

                current = current.getNext();
            }
            return valveFunc;
        }

        private BpcValveFunc concurrent(List<BpcValveFunc> parList) {
            return (ctx) -> {
                List<CompletableFuture<Void>> futureList = new ArrayList<>(parList.size());
                for (BpcValveFunc parValveFunc : parList) {
                    CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                        long s = System.currentTimeMillis();
                        parValveFunc.invoke(ctx);
                        log.info("[{}] Parallel Valve, spent: {}ms", ctx.getId(), System.currentTimeMillis() - s);
                    });
                    futureList.add(completableFuture);
                }
                CompletableFuture<Void> future = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));
                try {
                    future.get(1000L, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    log.error("[{}] 执行concurrent 异常", ctx.getId(), e);
                    throw new BpcPipelineException(String.format("执行concurrent 异常, id: %s", ctx.getId()));
                }

                return true;
            };
        }

        private BpcValveFunc logAroundFunc(BpcValve bpcValve, String valveName) {
            Preconditions.checkArgument(bpcValve != null);

            return (ctx) -> {
                long s = System.currentTimeMillis();
                try {
                    bpcValve.invoke(ctx);

                    if (!ctx.isBroken() && bpcValve instanceof BpcRollback) {
                        this.pipeline.rollbackFunc = this.pipeline.rollbackFunc.andThen(this.logAroundRollback((BpcRollback) bpcValve, valveName));
                    }

                    log.info("[{}] valve: {}, spent: {}ms", ctx.getId(), valveName, System.currentTimeMillis() - s);
                    return true;
                } catch (Exception e) {
                    // broke
                    log.error("[{}] 执行valve: {} 异常", ctx.getId(), valveName, e);
                    ctx.setMessage(e.getMessage());
                    ctx.setBroken(true);
                    throw new BpcPipelineException(String.format("执行valve: %s 异常, id: %s", valveName, ctx.getId()), e);
                }
            };
        }

        private BpcRollbackFunc logAroundRollback(BpcRollback bpcRollback, String valveName) {
            return (ctx) -> {
                long rs = System.currentTimeMillis();
                bpcRollback.rollback(ctx);
                log.info("[{}] rollback valve: {}, spent: {}ms", ctx.getId(), valveName, System.currentTimeMillis() - rs);
                return true;
            };
        }

        class BpcFuncPipForkBuilder implements BpcPipFork {

            List<FuncPipBuilder.BpcFuncPipForkBuilder.BpcFuncPipForkParBuilder> forkList = new ArrayList<>();
            ParallelValveModel parallelValveModel = new ParallelValveModel();

            public ParallelValveModel getParallelValveModel() {
                return parallelValveModel;
            }

            @Override
            public BpcPipForkAnd and(BpcValve bpcValve, String valveName) {
                FuncPipBuilder.BpcFuncPipForkBuilder.BpcFuncPipForkParBuilder forkAndBuilder = new FuncPipBuilder.BpcFuncPipForkBuilder.BpcFuncPipForkParBuilder(this, bpcValve, valveName);
                forkList.add(forkAndBuilder);

                parallelValveModel.getValveList().add(forkAndBuilder.getParLinkedModel());
                return forkAndBuilder;
            }

            @Override
            public BpcPipNext join() {
                return builder;
            }

            class BpcFuncPipForkParBuilder implements BpcPipForkAnd {
                FuncPipBuilder.BpcFuncPipForkBuilder forkBuilder;

                BpcDoublyLinkedModel parLinkedModel;

                BpcFuncPipForkParBuilder(FuncPipBuilder.BpcFuncPipForkBuilder forkBuilder, BpcValve bpcValve, String valveName) {
                    this.forkBuilder = forkBuilder;
                    parLinkedModel = new BpcDoublyLinkedModel("并行", new SerialValveModel(valveName, bpcValve));
                }

                @Override
                public BpcPipForkAnd and(BpcValve bpcValve, String valveName) {
                    return forkBuilder.and(bpcValve, valveName);
                }

                @Override
                public BpcPipForkAnd next(BpcValve bpcValve, String valveName) {
                    parLinkedModel.append(new SerialValveModel(valveName, bpcValve));
                    return this;
                }

                @Override
                public BpcPipNext join() {
                    return forkBuilder.join();
                }

                public BpcDoublyLinkedModel getParLinkedModel() {
                    return parLinkedModel;
                }
            }
        }
    }

}
