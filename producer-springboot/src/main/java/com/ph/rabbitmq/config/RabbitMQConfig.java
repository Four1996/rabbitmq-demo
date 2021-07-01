package com.ph.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-07-01 10:49
 **/
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME="boot_topic_exchange";
    public static final String Queue_NAME="boot_queue";

    // 1、交换机
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }
    // 2、Queue队列
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(Queue_NAME).build();
    }
    // 3、队列和交换机绑定关系 binding
    /**
        1、知道哪个队列
        2、知道哪个交换机
        3、routing key
     */
    @Bean()
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
