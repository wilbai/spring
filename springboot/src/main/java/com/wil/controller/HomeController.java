package com.wil.controller;

import com.wil.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by wil on 2017/12/7.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String hello(Model model, HttpSession session) {
        model.addAttribute("product", new Product(661,"Apple 苹果 iPhone X 全面屏手机 深空灰色 全网通 64GB",
                "【原封国行正品 全国联保】送钢化膜+保护壳 全面屏，科技全绽放。 苹果8plus",7999,9999));
        session.setAttribute("accountId",1001);
        model.addAttribute("placeList", Arrays.asList("德国", "美国", "中国"));
        model.addAttribute("inventory",5);
        model.addAttribute("attitude","十分满意");
        return "home";
    }
}
