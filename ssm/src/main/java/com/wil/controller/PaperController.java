package com.wil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wil on 2018/5/17.
 */
@Controller
public class PaperController {

    @GetMapping("/paper")
    public String doPaper(Model model) {
        List<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add("2");
        idList.add("3");
        idList.add("4");
        idList.add("5");
        idList.add("6");

        model.addAttribute("idList", idList);
        return "paperDetail";
    }

    @PostMapping("/paper")
    @ResponseBody
    public void daPaper(HttpServletRequest request) {

        Enumeration qs = request.getParameterNames();

        String[] selected = null;
        while (qs.hasMoreElements()) {
            //多选题使用getParameterValues 返回数组
            //单选题使用getParameter 返回单个字符

            selected = request.getParameterValues((String) qs.nextElement());
            for(String s : selected) {
                System.out.print(s + " ");
            }
                System.out.println("======");
        }

    }

}
