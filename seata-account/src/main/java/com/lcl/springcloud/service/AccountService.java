package com.lcl.springcloud.service;

import java.math.BigDecimal;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/515:58
 */
public interface AccountService {
    void decrease(Long userId, BigDecimal deMoney) throws Exception;
}
