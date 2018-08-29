package com.wil.controller;

import com.wil.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by wil on 2017/12/6.
 */
@Controller
public class LoginController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String mobile, String password) {
        try {
            consumerService.login(mobile, password);
            return "redirect:/";
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


}
