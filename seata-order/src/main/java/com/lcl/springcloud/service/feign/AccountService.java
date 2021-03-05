package com.lcl.springcloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/517:18
 */
@FeignClient("seata-account-service")
public interface AccountService {
    @GetMapping("/account/decrease")
    Boolean decrease(@RequestParam("userId") Long userId,@RequestParam("deMoney") BigDecimal deMoney);
}
