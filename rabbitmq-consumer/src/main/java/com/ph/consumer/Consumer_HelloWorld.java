package com.ph.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-06-30 10:57
 **/
public class Consumer_HelloWorld {
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
        //注意如果没有一个名叫helloworld的队列，则会创建该队列，如果有则不会创建。
        channel.queueDeclare("helloworld",true,false,false,null);
    }
}
