package com.wil.service.impl;

import com.wil.entity.Account;
import com.wil.entity.AccountExample;
import com.wil.entity.Json;
import com.wil.mapper.AccountMapper;
import com.wil.service.EmployeeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/6.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void save(Account account) {
        accountMapper.insertSelective(account);
    }

    @Override
    public List<Account> findAllByParam(Map<String, Object> param) {
        List<Account> accountList = accountMapper.findByDeptId(param);
        return accountList;
    }

    @Override
    public Json login(Account account) {
        Json json = new Json();
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andMobileEqualTo(account.getMobile());
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        String salt = "mysql";
        String encodedPass = DigestUtils.md5Hex(salt + account.getPassword());
        System.out.println(encodedPass);
        Account account1 = accountList.get(0);
        if(account1 != null && encodedPass.equals(account1.getPassword())) {
            json.setState("true");
        } else {
            json.setState("false");
            json.setMessage("手机号后密码错误");
        }
        return json;
    }
}
