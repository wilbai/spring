package com.wil.crm.service.impl;

import com.wil.crm.entity.Account;
import com.wil.crm.exception.AuthenticationException;
import com.wil.crm.mapper.AccountMapper;
import com.wil.crm.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wil on 2017/11/7.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account login(String mobile, String password) {
        Account account = accountMapper.findByMobile(mobile);
        String salt = "mysql";
        String codePass = DigestUtils.md5Hex(salt + password);
        if(account != null & codePass.equals(account.getPassword())) {
            logger.info("{} 在 {} 登录成功",account.getUserName(), new Date());
            return account;
        } else {
            throw new AuthenticationException("用户名或密码错误");
        }


    }
}
