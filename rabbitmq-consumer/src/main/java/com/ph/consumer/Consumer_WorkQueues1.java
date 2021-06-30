package com.ph.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-06-30 10:57
 **/
public class Consumer_WorkQueues1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、建立连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //2、设置参数
        factory.setHost("localhost");//ip地址，默认为本机地址
        factory.setPort(5672);
        factory.setVirtualHost("/ph");//虚拟机,默认值为/
        factory.setUsername("panhao");//用户名，默认guest
        factory.setPassword("panhao");//密码，默认是guest
        //3、创建连接Connection
        Connection connection = factory.newConnection();
        //4、创建Channel
        Channel channel = connection.createChannel();
        //5、创建队列Queue
        /***
         queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         参数：
         1、queue:队列名称
         2、durable：是否持久化，当mq重启之后，还在
         3、exclusive：
         *是否独占。只能有一个消费者监听这队列
         *          *当Connection关闭时，是否删除队列
         *  4、autoDelete：是否自动删除。当没有Consumer时，自动删除掉。
         *  5、arguments：参数。
         */
        //注意如果没有一个名叫work_queues的队列，则会创建该队列，如果有则不会创建。
        channel.queueDeclare("work_queues",true,false,false,null);

        //接收消息
        /***
         basicConsume(String queue, boolean autoAck, Consumer callback)
         参数:
            1、queue:队列名称
            2、autoAck：是否自动确认
            3、callback：回调对象
         */
        Consumer consumer=new DefaultConsumer(channel){
            /**
             回调方法。当收到消息后，会自动执行该方法。
                 1、consumerTag:标识
                 2、envelope：获取一些信息。比如交换机的信息，路由key。。。
                 3、properties：配置信息
                 4、body：数据
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // System.out.println("consumerTag:"+consumerTag);
                // System.out.println("envelope"+envelope.getExchange());
                // System.out.println("RoutingKey:"+envelope.getRoutingKey());
                // System.out.println("properties:"+properties);
                System.out.println("body:"+new String(body));
            }
        };
        channel.basicConsume("work_queues",true,consumer);
        //要关闭资源吗？当然是不要，因为消费者相当于一个监听程序，当消息来了之后，我再接受它，所以要一直保持连接的状态。



    }
}
