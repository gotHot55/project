package com.lcl.springcloud.service;

import com.lcl.springcloud.model.ChangeEventModel;
import com.lcl.springcloud.repository.AccountInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2411:18
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Override
    @Transactional
    public void updateAmount(ChangeEventModel changeEventModel) {
        log.info("bank2更新本地账号，账号：{},金额：{}",changeEventModel.getAccountNo(),changeEventModel.getAmount());

        int existTx = accountInfoRepository.isExistTx(changeEventModel.getTxNo());
        if (existTx > 0) {
            return;
        }
        accountInfoRepository.updateAmount(changeEventModel.getAmount(), changeEventModel.getAccountNo());
        accountInfoRepository.addTx(changeEventModel.getTxNo());
        log.info("更新本地事务执行成功，本次事务号: {}", changeEventModel.getTxNo());
        if(changeEventModel.getAmount() == 4){
            throw new RuntimeException("人为制造异常");
        }
    }
}
