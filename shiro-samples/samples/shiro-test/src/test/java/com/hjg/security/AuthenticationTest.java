package com.hjg.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("user", "12345");
    }

    @Test
    public void testAuthentication() {
        //1.构建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        //2. 获取主体，并提交登录请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("user", "12345");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.logout();

        System.out.println("isAuthenticated: " + subject.isAuthenticated());
    }
}
