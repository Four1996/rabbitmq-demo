# rabbitmq-demo
rabbitmq简单练习<br/>
<br/>
**"Hello World"工作模式：**<br/>
1、一个生产者对应一个消费者。<br/>
**"Work Queue工作模式"：**<br/>
1、再一个队列中如果有多个消费者，那么消费者之间对于同一个消息的关系是**竞争**的关系。<br/>
2、<font color='red'>Work Queues</font>对于任务过重或者任务较多的情况使用工作队列可以提高任务处理的速度。例如：短信服务部署多个，只需要一个节点成功发送即可。<br/>
**"PubSub工作模式"：**<br/>
1、一个消费者可以绑定多个消息队列。<br/>
**"Routing工作模式"：**<br/>
1、通过指定路由key来限制消费者就收信息的权限。即将不同的信息，按路由key进行分类，有选择地被不同的消费者接收到。<br/>
2、一个消费者可以绑定多个消息队列。<br/>
**"Topics工作模式"：**<br/>
1、与路由模式不同的是，采用通配符的模式进行路由key的匹配。<br/>
2、同样，一个消费者可以绑定多个消息队列。<br/>
**SpringBoot整合RabbitMQ:**<br/>
1、SpringBoot提供了快速整合RabbitMQ的方式<br/>
2、基本信息在yml中配置（rabbitmq主机地址、端口号、用户名、密码、虚拟机路径）,队列交互机以及绑定关系在配置类中使用Bean的方式配置<br/>
3、生产端直接注入RabbitTemlplate完成消息发送<br/>
4、消费端直接使用@RabbitListener完成消息接受。<br/>
<br/>
**死信队列**<br/>
消息成为<font color='red'>死信</font>的三种情况：<br/>
1、队列消息长度到达限制<br/>
2、消费者拒接消费消息，basicNack/basicReject，并且不把消息重新放入原目标队列，requeue=false；<br/>
3、原队列存在消息过期设置，消息到达超时时间未被消费。<br/>
<br/>
**消息补偿**<br/>
