package com.hjg.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthoritiesTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("user", "12345", "admin", "user");
    }

    @Test
    public void authorityTest() {
        //1，构建security环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("user", "12345");
        subject.login(token);

        //断言已经认证
        Assert.assertTrue(subject.isAuthenticated());

        //判断是否具有admin和user权限，如果没有会报错
        subject.checkRoles("admin", "user");
    }
}
