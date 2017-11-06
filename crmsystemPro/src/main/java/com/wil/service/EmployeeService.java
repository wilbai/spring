package com.wil.service;

import com.wil.entity.Account;
import com.wil.entity.Json;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/6.
 */
public interface EmployeeService {

    void save(Account account);

    List<Account> findAllByParam(Map<String, Object> param);

    Json login(Account account);
}
