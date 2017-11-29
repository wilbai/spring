package com.wil.crm.auth;

import com.wil.crm.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by wil on 2017/11/24.
 */
public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Account getCurrentAccount() {
        return (Account) getSubject().getPrincipal();
    }

}
