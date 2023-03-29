package com.hjg.security;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

public class EncryTest {

    @Test
    public void test1() {
        String password = "123456";
        String encodePassword = new Md5Hash(password).toString();

        System.out.println(encodePassword);
    }

    /**
     * 加盐（随机数），并多次加密。
     */
    @Test
    public void testWithSalt() {
        String password = "123456";
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();

        int time = 2;
        String algorithm = "md5";

        String encodedPwd = new SimpleHash(algorithm, password, salt, time).toString();
        //不同于普通的123456的md5加密e10adc3949ba59abbe56e057f20f883e
        System.out.println(encodedPwd);
    }
}
