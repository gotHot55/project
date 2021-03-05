package com.lcl.springcloud.controller;

import com.lcl.springcloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2817:19
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/get/{id}")
    public ModelMap get(@PathVariable("id") Integer id) {
        return loginService.getById(id);
    }

    @GetMapping(value = "/getList")
    public ModelMap getList() {
        return loginService.getList();
    }

    @GetMapping(value = "/del/{id}")
    public ModelMap del(@PathVariable Integer id) {
        return loginService.getDel(id);
    }
}
