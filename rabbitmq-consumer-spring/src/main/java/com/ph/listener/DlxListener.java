package com.ph.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitmq
 * @description:
 * ConsumerACK机制：
 * 1、设置手动签收。acknowledge="manual"
 * 2、让监听器类实现ChannelAwareMessageListener接口
 * 3、如果消息成功处理，则调用channel的basicAck（）签收
 * 4、如果消息处理失败，则调用channel的basicNack（）拒绝签收，broker重新发送给consumer
 *
 * @author: panhao
 * @date: 2021-07-01 15:36
 **/
@Component
public class DlxListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            // 1、转换消息
            System.out.println(new String(message.getBody()));
            // 2、处理业务逻辑
            System.out.println("处理业务逻辑...");
            int i=3/0;
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
            System.out.println("出现异常拒绝接受");
             // 第三个参数叫：requeue：重回队列。如果设置为true，则消息会重新回到队列，broker会重新发送该消息给消费端。
             // 拒绝签收，不重回队列 requeue=false
             channel.basicNack(deliveryTag,true,false);
        }

    }


}
