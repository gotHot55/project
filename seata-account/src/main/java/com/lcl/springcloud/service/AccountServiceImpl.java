package com.lcl.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcl.springcloud.model.Account;
import com.lcl.springcloud.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/516:03
 */
@Service
public class AccountServiceImpl implements AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Resource
    private AccountRepository accountRepository;
    @Override
    public void decrease(Long userId, BigDecimal deMoney) throws Exception {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Account account = accountRepository.selectOne(wrapper);
        if (account != null) {
            if (deMoney.compareTo(account.getResidue()) > 0) {
                logger.error("消费金额超过你能承受的极限");
                throw new Exception("消费金额超过你能承受的极限！");
            }
            account.setResidue(account.getResidue().subtract(deMoney));
            account.setUsed(account.getUsed().add(deMoney));
            accountRepository.update(account, wrapper.eq("user_id", userId));
        }else {
            logger.error("userid:{},数据不存在", userId);
            throw new NullPointerException("数据不存在");
        }
    }
}
