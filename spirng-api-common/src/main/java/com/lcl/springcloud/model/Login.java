package com.lcl.springcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2311:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login implements Serializable {
    private Integer id;
    private String user;
    private String password;

}
