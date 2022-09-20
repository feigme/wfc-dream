# FSM springboot starter

> FSM（finite-state machine）有限状态机，简称状态机，是表示有限个状态以及在这些状态之间的转移和动作等行为的数学模型。

状态机模型中的4个要素

- **现态**：是指当前所处的状态
- **条件**：又称为“事件”。当一个条件被满足，将会触发一个动作，或者执行一次状态的迁移
- **动作**：条件满足后执行的动作。动作执行完毕后，可以迁移到新的状态，也可以仍旧保持原状态。动作不是必需的，当条件满足后，也可以不执行任何动作，直接迁移到新状态
- **次态**：条件满足后要迁往的新状态。“次态”是相对于“现态”而言的，“次态”一旦被激活，就转变成新的“现态”了

> “现态”和“条件”是因，“动作”和“次态”是果。

## 常见类型状态机
### Squirrel State Machine 
文档地址: ()[http://hekailiang.github.io/squirrel/]

### Spring Statemachine
文档地址：()[https://docs.spring.io/autorepo/docs/spring-statemachine/1.0.0.M3/reference/htmlsingle/#sm-statecontext]

### cola 状态机
文档地址：()[https://github.com/alibaba/COLA/tree/master/cola-components/cola-component-statemachine]

### 对比
| 状态机类型 |体量 | 上手难易度 |
|----------|----|-----------|
|SquirrelStateMachine|相较于spring statemachine，squirrel的实现更为轻量，设计域也很清晰，对应的文档以及测试用例也很丰富 | 相较于spring statemachine，squirrel上手更容易
|SpringStateMachine  |StateMachine实例的创建比较重 , 以单例方式线程不安全，使用工厂方式对于类似订单等场景StateMachineFactory缓存订单对应的状态机实例意义不大，并且transition的注解并不支持StateMachineFactory |相较于squirrel , spring statemachine上手略难|
