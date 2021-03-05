package com.micro.lcl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void helloShiro() {
        DefaultSecurityManager manager = new DefaultSecurityManager();
        IniRealm realm = new IniRealm("classpath:shiro.ini");
        manager.setRealm(realm);
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("lcl1", "123");
        try {
            subject.login(token);
            System.out.println("验证成功");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println(subject.isAuthenticated());
//        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();

    }

}
