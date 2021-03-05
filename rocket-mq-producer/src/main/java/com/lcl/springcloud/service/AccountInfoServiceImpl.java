package com.lcl.springcloud.service;

import com.alibaba.fastjson.JSONObject;
import com.lcl.springcloud.model.ChangeEventModel;
import com.lcl.springcloud.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2317:06
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private AccountRepository accountRepository;

    @Override
    public void updateAmount(ChangeEventModel changeEventModel) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("changeEvent", changeEventModel);
        Message<String> message = MessageBuilder.withPayload(jsonObject.toJSONString()).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("producer_group_txmsg_bank", "account_bank", message, null);
//        rocketMQTemplate.send("account_bank", message);
        rocketMQTemplate.convertAndSend("account_bank",message);
        log.debug("send transcation message body=-->{},result=-->{} ", message.getPayload(), result.getLocalTransactionState());
    }

    @Override
    @Transactional
    public void doUpdateAccount(ChangeEventModel changeEventModel) {
        log.debug("开始更新本地任务，任务号：{}", changeEventModel.getTxNo());
        accountRepository.updateAmount(changeEventModel.getAmount() * -1, changeEventModel.getAccountNo());

        accountRepository.addTx(changeEventModel.getTxNo());
        if (changeEventModel.getAmount() == 2) {
            throw new RuntimeException("bank1更新本地事务时抛出异常");
        }
        log.debug("结束更新本地事务，事务号：{}", changeEventModel.getTxNo());
    }

}
