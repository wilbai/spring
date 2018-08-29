package com.wil.controller;

import com.wil.service.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wil on 2017/12/11.
 */
@RestController
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/dubbo")
    public String hello() {
        userService.sayHello("SpringBoot+dubbo");
        return "hello boot dubbo";
    }
}
