package com.wil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wil on 2018/8/22.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String index() {
        return "home";
    }

}
