package com.wil.crm.controller.interceptor;

import com.wil.crm.entity.Account;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录于否拦截器，放行静态资源，登录页
 * Created by wil on 2017/11/8.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String uri = request.getRequestURI();
        if ("/static".startsWith(uri) || "".equals(uri) || "/".equals(uri)) {
            return true;
        }
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("currentAccount");
        if (account != null) {
            return true;
        }
        response.sendRedirect("/");
        return false;
    }
}
