package com.lcl.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2316:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEventModel {
    private String accountNo;//账户
    private Double amount;//金额
    private String txNo;//事物号
}
