package com.wil.controller;

import com.wil.entity.User;
import com.wil.entity.UserExample;
import com.wil.service.UserService;
import com.wil.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wil on 2017/11/3.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id:\\d+}")
    @ResponseBody
    public User findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/user")
    public String findAll(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        //model.addAttribute("userId", userList.get(0).getId());
        //model.addAttribute("userAddressId", userList.get(0).getAddressId());
        return "user";
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> page(@RequestParam(required = false,
            defaultValue = "1", name = "p") Integer pageNo) {
        return userService.findByPageNo(pageNo);
    }



}
