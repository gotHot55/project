package com.lcl.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lcl.springcloud.mapper.LoginMapper;
import com.lcl.springcloud.mapper.UserMapper;
import com.lcl.springcloud.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/10/2314:26
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Login get(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public Integer insertUser(Login login) {

        return loginMapper.insert(login);
    }

    @Override
    public Integer delUser(Integer id) {
        return loginMapper.deleteById(id);
    }

    @Override
    public Integer updateUser(Login login) {
        return loginMapper.update(login,new QueryWrapper<Login>().eq("id", login.getId()));
    }

    @Override
    public Login getById(Integer id) {
        return loginMapper.selectById(id);
    }

    @Override
    public List<Login> getUsers() {
        return loginMapper.selectList(new QueryWrapper<Login>());
    }

    @Override
    public Integer getUser(Login login) {
        return loginMapper.selectCount(new QueryWrapper<Login>().eq("user", login.getUser()).eq("password", login.getPassword()));
    }

}
