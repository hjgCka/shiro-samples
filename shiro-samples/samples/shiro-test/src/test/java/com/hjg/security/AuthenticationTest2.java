package com.hjg.security;

import com.hjg.security.realm.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class AuthenticationTest2 {

    @Test
    public void testAuthentication() {
        MyRealm myRealm = new MyRealm();

        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(myRealm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("user", "12345");
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        /**
         * 没有对应角色会抛出异常
         */
        subject.checkRoles("admin", "user");

        /**
         * 角色没有对应权限会抛出异常
         */
        subject.checkPermission("user:add");
    }
}
