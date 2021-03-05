package com.lcl.springcloud.service;

import com.lcl.springcloud.model.Login;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2818:14
 */
@Component
public class UserFullBackServiceImpl implements LoginService {

    @Override
    public ModelMap getById(Integer id) {
        ModelMap map = new ModelMap();
        Login login = new Login(id,"default","123456");
        map.addAttribute("code", 305)
                .addAttribute("message", "查询失败，回调函数")
                .addAttribute("login", login);
        return map;
    }

    @Override
    public ModelMap getList() {
        ModelMap map = new ModelMap();
        Login login = new Login(-1,"default","123456");
        map.addAttribute("code", 305)
                .addAttribute("message", "查询失败，回调函数")
                .addAttribute("login", login);
        return map;
    }

    @Override
    public ModelMap getDel(Integer id) {
        ModelMap map = new ModelMap();
        map.addAttribute("code", 305)
                .addAttribute("message", "删除失败，回调函数");
        return map;
    }
}
