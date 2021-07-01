package com.ph.test;

import org.junit.Test;
import org.junit.runner.RunWith;
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
        确认模式：
            步骤：
                1、确认模式开启：ConnectionFactory中开启publisher-confirms="true"
                2、在rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm(){
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
                if (ack){
                // 接受成功
                    System.out.println("接受成功消息"+cause);
                }else{
                // 接受失败
                    System.out.println("接受失败消息"+cause);
                // 做一些失败处理，让消息再次发送.
                }
            }
        });
        //3、发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm","message comfirm....");
    }
}
