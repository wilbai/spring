package com.wil.service;

import com.wil.entity.AccountDept;
import com.wil.entity.Dept;

/**
 * Created by wil on 2017/11/6.
 */
public interface DeptService {

    Dept findById(Integer id);

    void saveAccountDept(AccountDept accountDept);
}
