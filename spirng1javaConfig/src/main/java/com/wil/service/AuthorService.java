package com.wil.service;

import com.wil.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by wil on 2017/10/30.
 */
@Service

public class AuthorService {

    @Autowired
    private UserDao userDao;

    @Transactional(rollbackFor = RuntimeException.class,isolation =
    Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = true)
    public void save() {
        userDao.save();
        if(1 == 1) {
            throw new RuntimeException();
        }
        userDao.save();
    }
}
