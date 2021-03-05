package com.lcl.springcloud.controller;

import com.lcl.springcloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/516:34
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Resource
    private AccountService accountService;

    @GetMapping(value = "/decrease")
    public boolean decrease(Long userId, BigDecimal deMoney) {
        try {
            accountService.decrease(userId, deMoney);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AccountService出错！！");
            return false;
        }
        return true;
    }
}
