package com.lcl.springcloud.controller;

import com.lcl.springcloud.model.Login;
import com.lcl.springcloud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2310:43
 */
@RestController
public class HelloController {
    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/get/{id}")
    public ModelMap getById(@PathVariable("id") Integer id) {

        ModelMap map = new ModelMap();
        Login login = loginService.getById(id);
        if (login != null && !(" ".equals(login))) {
            map.addAttribute("code", 200)
                    .addAttribute("message", "查询成功")
                    .addAttribute("login", login);
        } else {
            map.addAttribute("code", 400)
                    .addAttribute("message", "查询失败")
                    .addAttribute("login", login);
        }
        return map;
    }

    @PostMapping(value = "/add")
    public ModelMap addUsr(@RequestBody Login login) {
        ModelMap map = new ModelMap();
        Integer one = loginService.getUser(login);
        if (one != 0) {
            map.addAttribute("code", 400)
                    .addAttribute("message", "用户名密码已存在");
        }else {
            Integer result = loginService.insertUser(login);
            if (result != 0) {
                map.addAttribute("code", 200)
                        .addAttribute("message", "添加成功")
                        .addAttribute("data", login);
            }else {
                map.addAttribute("code", 403)
                        .addAttribute("message", "添加失败");
            }
        }
        return map;
    }

    @GetMapping(value = "/getList")
    public ModelMap getList() {
        ModelMap modelMap = new ModelMap();
        List<Login> users = loginService.getUsers();
        if (users.size() != 0 ) {
            modelMap.addAttribute("code", 200)
                    .addAttribute("message", "查询成功")
                    .addAttribute("login", users);
        }else {
            modelMap.addAttribute("code", 400)
                    .addAttribute("message", "查询失败,无数据！")
                    .addAttribute("login", users);
        }
        return modelMap;
    }

    @GetMapping(value = "/delById/{id}")
    public ModelMap delById(@PathVariable("id") Integer id) {
        ModelMap map = new ModelMap();
        Login login = loginService.getById(id);
        if (login != null) {
            loginService.delUser(id);
            map.addAttribute("code", 200)
                    .addAttribute("message", "删除成功")
                    .addAttribute("data", login);
        }else {
            map.addAttribute("code", 400)
                    .addAttribute("message", "删除失败,不存在数据");
        }
        return map;
    }
}
