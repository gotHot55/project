package com.lcl.springcloud.service;

import com.lcl.springcloud.model.Login;
import org.springframework.ui.ModelMap;

import java.util.concurrent.Future;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2710:47
 */


public interface UserService {
    ModelMap get(Integer id);

    Future<Login> getUserFuture(Integer id);
}
