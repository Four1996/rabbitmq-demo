# rabbitmq-demo
rabbitmq简单练习<br/>
<br/>
**"Hello World"工作模式**：一个生产者对应一个消费者。<br/>

**"Work Queue工作模式"**：<br/>
1、再一个队列中如果有多个消费者，那么消费者之间对于同一个消息的关系是**竞争**的关系。<br/>
2、<font color='red'>Work Queues</font>对于任务过重或者任务较多的情况使用工作队列可以提高任务处理的速度。例如：短信服务部署多个，只需要一个节点成功发送即可。