package com.wil.crm.controller;

import com.wil.crm.auth.ShiroUtil;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Dept;
import com.wil.crm.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wil on 2017/11/7.
 */
@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String login() {

        Subject subject = ShiroUtil.getSubject();

        if(subject.isAuthenticated()) {
            //被验证的来到login,认为要切换帐号
            subject.logout();
        }

        if(!subject.isAuthenticated() && subject.isRemembered()) {
            //被记住的但未被验证 去home页
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/")
    public String login(String mobile, String password, boolean rememberMe,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request) {
        try{
            Subject subject = ShiroUtil.getSubject();
            String salt = "mysql";
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(mobile, new Md5Hash(password, salt).toString(), rememberMe);
            subject.login(usernamePasswordToken);

            Account account = ShiroUtil.getCurrentAccount();
            List<Dept> deptList = accountService.findDeptListByAccountId(account.getId());
            Session session = subject.getSession();
            session.setAttribute("deptList", deptList);
            session.setAttribute("currentAccount", account);

            //跳转到登录前的url
            String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }
            return "redirect:" + url;
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message", "帐号或密码错误");
            return "redirect:/";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }


}
