package com.ph.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq
 * @description:
 * @author: panhao
 * @date: 2021-07-02 16:45
 **/
@Component
public class OrderListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 1、转换消息
            System.out.println(new String(message.getBody()));
            // 2、处理业务逻辑
            System.out.println("处理业务逻辑...");
            System.out.println("根据订单id查询其状态....");
            System.out.println("判断状态是否为支付成功");
            System.out.println("取消订单，回滚库存...");
            // 3、手动签收
            /**
             basicAck(long deliveryTag, boolean multiple)
             参数：
             deliveryTag：表示当前收到消息的类型标签
             multiple：如果为true，签收所有的消息。
             */
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            // 4、拒绝签收
            // 第三个参数叫：requeue：重回队列。如果设置为true，则消息会重新回到队列，broker会重新发送该消息给消费端。
            channel.basicNack(deliveryTag,true,false);
        }

    }
}
