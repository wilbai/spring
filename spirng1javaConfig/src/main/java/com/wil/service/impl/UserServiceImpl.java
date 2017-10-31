package com.wil.service.impl;

import com.wil.dao.UserDao;
import com.wil.entity.User;
import com.wil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wil on 2017/10/30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        userDao.save();
    }

    @Override
    public Long count() {
        return userDao.count();
    }

    @Override
    @Transactional
    public void insert(User user) {

        userDao.insert(user);
        if(1 == 1) {
            throw new RuntimeException("on purpose exception");
        }
        userDao.insert(user);
    }

}
