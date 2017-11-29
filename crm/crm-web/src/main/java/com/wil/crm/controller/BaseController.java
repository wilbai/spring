package com.wil.crm.controller;

import com.wil.crm.auth.ShiroUtil;
import com.wil.crm.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * Created by wil on 2017/11/10.
 */
public abstract class BaseController {

    /**
     * 从session中获取当前登录account
     * @param session
     * @return
     */
    public Account getCurrentAccount(HttpSession session) {
        return (Account) ShiroUtil.getCurrentAccount();
    }

}
