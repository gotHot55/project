package com.lcl.springcloud.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2714:13
 */
public class TemplateProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /*public static void main(String[] args) {
        RocketMQTemplate template = new RocketMQTemplate();
        Message<String> payload = MessageBuilder.withPayload("hello rocketmq").build();
        template.send(payload);
        System.out.println("payload:-->"+payload);

    }*/
}
