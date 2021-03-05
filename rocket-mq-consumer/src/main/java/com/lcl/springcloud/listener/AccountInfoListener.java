package com.lcl.springcloud.listener;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.lcl.springcloud.model.ChangeEventModel;
import com.lcl.springcloud.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2411:24
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "account_bank",consumerGroup = "producer-bank2")
public class AccountInfoListener implements RocketMQListener<String> {
    @Resource
    private AccountInfoService accountInfoService;

    @Override
    public void onMessage(String message) {
        log.debug("开始消费消息：{}", message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        String changeEvent = jsonObject.getString("changeEvent");
        ChangeEventModel model = JSONObject.parseObject(changeEvent, ChangeEventModel.class);
        model.setAccountNo("2");
        accountInfoService.updateAmount(model);

    }
}
