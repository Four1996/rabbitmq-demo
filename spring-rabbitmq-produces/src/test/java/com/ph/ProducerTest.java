package com.ph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-06-30 22:10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {
    //1、注入 RabbitTemplate
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld(){
        //2、发送消息
        rabbitTemplate.convertAndSend("spring_queue","hello,world spring....");

    }
    /***
        发送fanout消息
     */
    @Test
    public void testFanout(){
        //2、发消息
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","spring fanout.....");
    }
    /***
        发送topics消息
     */
    @Test
    public void testTopics(){
        //2、发消息
        rabbitTemplate.convertAndSend("spring_topic_exchange","panhao.hehe.haha","spring topics.....");
    }
}
