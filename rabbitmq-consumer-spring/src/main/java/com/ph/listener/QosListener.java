package com.ph.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq
 * @description:
 * consumer的限流机制
 *    1、确保ack确认机制为手动确认
 *    2、listener-container配置属性
 *          perfetch=xxx，表示消费端每次从mq拉取xxx条消息来消费，直到手动确认消费完毕后，才会继续拉取下一批消息。
 *
 * @author: panhao
 * @date: 2021-07-01 20:46
 **/
@Component
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        // 1、获取消息
        System.out.println(new String(message.getBody()));

        // 2、处理业务逻辑

        // 3、签收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}
