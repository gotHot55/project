package com.lcl.springcloud.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2717:29
 */
public class OrderdProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("producer_group");
        producer.setNamesrvAddr("localhost:9876");
        String[] tags = {"TagA", "TagB", "TagC","TagD","TagE","TagF"};
        producer.start();
        for (int i = 0; i < 10; i++) {
            int orderId = i % 10;
            Message message = new Message("TopicA", tags[i % tags.length], "key" + i, ("发布者发布消息" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Integer id = (Integer) o;
                    int index = id % list.size();
                    return list.get(index);
                }
            }, orderId);
            System.out.println("发布消息：\n" + sendResult);
        }
        producer.shutdown();
        System.out.println("发布消息结束！");
    }
}
