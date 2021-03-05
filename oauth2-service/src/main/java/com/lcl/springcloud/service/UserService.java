package com.lcl.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Todo
 *
 * @author Administrator
 * @date 2020/11/214:20
 */
@Service
public class UserService implements UserDetailsService {

    private List<User> list;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initDate() {
        String password = passwordEncoder.encode("123456");
        list = new ArrayList<>();
        list.add(new User( "lcl", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        list.add(new User( "tom", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        list.add(new User("jerry", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> findUserList = list.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
