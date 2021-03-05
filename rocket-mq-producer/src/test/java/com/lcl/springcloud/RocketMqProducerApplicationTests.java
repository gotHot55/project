package com.lcl.springcloud;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
class RocketMqProducerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void contextLoads() {
        Message<String> payload = MessageBuilder.withPayload("hello rocketmq").build();
        rocketMQTemplate.send("bank",payload);
        System.out.println("payload:-->"+payload);
    }

}
