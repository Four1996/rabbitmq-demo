package com.ph.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-06-30 22:28
 **/
public class TopicListenerStar implements MessageListener {
    @Override
    public void onMessage(Message message) {
        // 打印消息
        System.out.println(new String(message.getBody()));
    }
}
