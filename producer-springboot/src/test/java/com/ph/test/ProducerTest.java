package com.ph.test;

import com.ph.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-07-01 10:59
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {
    @Autowired
    //1、注入RabbitTemplate
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.haha","boot mq hello!!!!!");
    }
}
