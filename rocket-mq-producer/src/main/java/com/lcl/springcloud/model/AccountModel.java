package com.lcl.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/2316:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
    private Integer id;
    private String accountName;
    private String accountNo;
    private String accountPassword;
    private Double accountBalance;

}
