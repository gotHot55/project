package com.lcl.springcloud.listener;

import com.alibaba.fastjson.JSONObject;
import com.lcl.springcloud.model.ChangeEventModel;
import com.lcl.springcloud.repository.AccountRepository;
import com.lcl.springcloud.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2317:32
 */
@Component
@Slf4j
@RocketMQTransactionListener(txProducerGroup = "producer_group_txmsg_bank")
public class AccountTxmsgListener implements RocketMQLocalTransactionListener {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountInfoService accountInfoService;
    //消息发送成功回调此方法，此方法执行本地事务
    @Override
    @Transactional
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String jsonString = new String((byte[]) message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            ChangeEventModel accountChange = JSONObject.parseObject(jsonObject.getString("changeEvent"), ChangeEventModel.class);
            accountInfoService.doUpdateAccount(accountChange);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("executeLocalTransaction 事务执行失败",e);
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    //此方法检查事务执行状态
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        RocketMQLocalTransactionState state;
        JSONObject jsonObject = JSONObject.parseObject(new String((byte[]) message.getPayload()));
        ChangeEventModel accountChange = JSONObject.parseObject(jsonObject.getString("changeEvent"), ChangeEventModel.class);
        int existTx = accountRepository.isExistTx(accountChange.getTxNo());
        if (existTx > 0) {
            state = RocketMQLocalTransactionState.COMMIT;
        }else {
            state = RocketMQLocalTransactionState.UNKNOWN;
        }
        return state;

    }
}
