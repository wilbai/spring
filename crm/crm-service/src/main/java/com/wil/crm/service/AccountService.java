package com.wil.crm.service;

import com.wil.crm.entity.Account;

/**
 * Created by wil on 2017/11/7.
 */
public interface AccountService {

    Account login(String mobile, String password);
}
