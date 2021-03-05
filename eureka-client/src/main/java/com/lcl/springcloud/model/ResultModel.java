package com.lcl.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2311:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {
    private Login login;
    private Integer code;
    private String message;

    public ResultModel(Login login, Integer code) {
        this(login, code, null);
    }

}
