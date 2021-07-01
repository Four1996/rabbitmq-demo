package com.ph.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-07-01 14:42
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 确认模式：
     * 步骤：
     * 1、确认模式开启：ConnectionFactory中开启publisher-confirms="true"
     * 2、在rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm() {
        //2、定义回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             correlationData:相关配置信息
             ack: exchange交换机是否成功收到消息。true成功，false代表失败
             cause: 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm方法被执行了");
                if (ack) {
                    // 接受成功
                    System.out.println("接受成功消息" + cause);
                } else {
                    // 接受失败
                    System.out.println("接受失败消息" + cause);
                    // 做一些失败处理，让消息再次发送.
                }
            }
        });
        //3、发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message comfirm....");
    }

    /**
     * 回退模式：当消息发送给Exchange后，Exchange路由到Queue失败时才会执行ReturnCallBack
     * 步骤：
     * 1、开启回退模式:publisher-returns="true"
     * 2、设置ReturnCallBack
     * 3、设置Exchange处理消息的模式：
     *      1、如果消息没有路由到Queue，则丢弃消息（默认）
     *      2、如果消息没有路由到Queue，返回给消息发送方ReturnCallBack
     */
    @Test
    public void testReturn() {
        // 设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);
        //2、设置ReturnCallBack
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             message: 消息对象
             replayCode: 失败错误码
             replayText: 错误信息
             exchange: 交换机
             routingKey: 路由键
             */
            @Override
            public void returnedMessage(Message message, int replayCode, String replayText, String exchange, String routingKey) {
                System.out.println("return执行了。。。。。");
                System.out.println(message);
                System.out.println(replayCode);
                System.out.println(replayText);
                System.out.println(exchange);
                System.out.println(routingKey);
                // 处理
            }
        });
        //3、发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message comfirm....");
    }

    @Test
    public void testSend() {

        for (int i = 0; i < 10; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message comfirm....");
        }

    }

    /**
     * TTL:过期时间
     * 1、队列统一过期
     *
     * 2、消息单独过期
     *
     *    如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准。
     *    如果队列过期后，会将队列所有消息全部移除
     *    消息过期后，只有消息在队列顶端，才会判断其是否过期（移除掉）
     */
    @Test
    public void testTtl() {

        // for (int i=0;i<10;i++){
        //     // 发送消息
        //     rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.hello","message ttl....");
        // }


        //消息的后处理对象,设置一些消息的参数信息。
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 1、设置message的信息
                message.getMessageProperties().setExpiration("5000");//消息的过期时间
                // 2、返回消息
                return message;
            }
        };

        // 消息单独过期
        // rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...", messagePostProcessor);

        for (int i=0;i<10;i++){
            if (i==5){
                //消息单独过期
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...", messagePostProcessor);

            }else{
                // 不过期的消息
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...");

            }
        }
    }
}
