# BPC springboot starter

> BPC（Business Process Choreography）代表 业务流程编排

使用方法

```groovy
BpcPipeline pipe = BpcFuncPipelineBuilder.newInvocation("测试流程")
        .next(testBeanDemoA, "aaa1")
        .next(testBeanDemoB, "bbb1")
        .next(testBeanDemoC, "ccc1")
        .fork()
            .and(testBeanDemoD, "ddd1")
            .and(testBeanDemoE, "eee1")
            .and(testBeanDemoF, "fff1")
        .join()
        .next(testBeanDemoG, "ggg1")
        .end();

pipe.invoke(new BpcTodoContext() {});
```