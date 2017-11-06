package com.wil.service.impl;

import com.wil.entity.AccountDept;
import com.wil.entity.Dept;
import com.wil.mapper.AccountDeptMapper;
import com.wil.mapper.DeptMapper;
import com.wil.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wil on 2017/11/6.
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private AccountDeptMapper accountDeptMapper;

    @Override
    public Dept findById(Integer id) {
        return deptMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveAccountDept(AccountDept accountDept) {
        accountDeptMapper.insert(accountDept);
    }
}
