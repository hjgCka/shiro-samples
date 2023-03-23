package com.hjg.shiro.springbootweb.samples.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-23 16:23
 */
@Controller
public class LoginController {

    @RequestMapping("/login.html")
    public String loginTemplate() {
        return "login";
    }
}
