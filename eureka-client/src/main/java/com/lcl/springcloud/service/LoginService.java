package com.lcl.springcloud.service;

import com.lcl.springcloud.model.Login;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2314:19
 */
public interface LoginService {

    Login get(Integer id);

    Integer insertUser(Login login);

    Integer delUser(Integer id);

    Integer updateUser(Login login);

    Login getById(Integer id);

    List<Login> getUsers();

    Integer getUser(Login login);

}
