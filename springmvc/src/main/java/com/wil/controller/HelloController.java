package com.wil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wil on 2017/11/2.
 */
@Controller
public class HelloController {

    //@RequestMapping(value = "/hello", method = {RequestMethod.GET,RequestMethod.POST})
    @GetMapping("hello")
    @PostMapping("hello")
    public void sayHello() {
        System.out.println("hello, SpringMVC!");
    }


}
