package com.lcl.springcloud.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.lcl.springcloud.model.Login;
import com.lcl.springcloud.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2710:42
 */
@RestController
public class HystrixController {
    @Autowired
    private UserService userService;
    @GetMapping(value = "/getCollapser")
    public List<Login> getCollapser() throws ExecutionException, InterruptedException {
        ArrayList<Login> list = new ArrayList<>();
        Future<Login> future1 = userService.getUserFuture(1);
        Future<Login> future2 = userService.getUserFuture(2);
        list.add(future1.get());
        list.add(future2.get());
        ThreadUtil.safeSleep(200);
        Future<Login> future3 = userService.getUserFuture(3);
        list.add(future3.get());
        return list;

    }
    @GetMapping(value = "/get/{id}")
    public ModelMap get(@PathVariable Integer id) {
        return userService.get(id);
    }
}
