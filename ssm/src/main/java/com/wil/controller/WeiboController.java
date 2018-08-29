package com.wil.controller;

import com.wil.entity.User;
import com.wil.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wil on 2018/1/6.
 */
@Controller
public class WeiboController {

    @Autowired
    private UserService userService;

    @GetMapping("/show")
    public String list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "weibo";
    }

    @GetMapping("/weibo")
    @ResponseBody
    public List<User> list(String maxId) {
        List<User> userList = null;
        if(StringUtils.isNotEmpty(maxId)) {
          userList = userService.findByMaxId(maxId);
        }
        return userList;
    }


}
