package com.wil.service;

import com.wil.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


/**
 * Created by wil on 2017/10/30.
 */
@Service
@Scope("prototype")
@Lazy
public class AuthorService {

    @Autowired
    private UserDao userDao;

    public void save() {
        userDao.save();
    }
}
