package com.wil.crm.controller;

import com.wil.crm.entity.Account;
import com.wil.crm.exception.AuthenticationException;
import com.wil.crm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by wil on 2017/11/7.
 */
@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/")
    public String login(String mobile, String password,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        try{
            Account account = accountService.login(mobile, password);
            session.setAttribute("currentAccount", account);
            return "redirect:/home";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
