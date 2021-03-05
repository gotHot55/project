package com.lcl.springcloud.controller;

import com.lcl.springcloud.model.ChangeEventModel;
import com.lcl.springcloud.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2410:31
 */
@RestController
@Slf4j
public class AccountController {
    @Resource
    private AccountInfoService accountInfoService;

    @GetMapping(value = "/transfer")
    public String transfer(@RequestParam("accountNo") String accountNo, @RequestParam("amount") Double amount) {
        String txNo = UUID.randomUUID().toString();
        ChangeEventModel changeEventModel = new ChangeEventModel(accountNo, amount, txNo);
        accountInfoService.updateAmount(changeEventModel);
        return "转账成功";
    }
}
