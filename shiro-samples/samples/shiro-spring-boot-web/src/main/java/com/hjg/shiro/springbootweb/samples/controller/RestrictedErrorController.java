package com.hjg.shiro.springbootweb.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description
 * @Author hjg
 * @Date 2023-03-23 16:25
 */
@Controller
public class RestrictedErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(ERROR_PATH)
    String error(HttpServletRequest request, Model model) {
        Map<String, Object> errorMap = errorAttributes.getErrorAttributes(new ServletWebRequest(request), ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        model.addAttribute("errors", errorMap);
        return "error";
    }
}
