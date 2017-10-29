package com.wil.service.impl;

import com.wil.dao.UserDao;
import com.wil.service.UserService;

/**
 * Created by wil on 2017/10/29.
 */
public class UserServiceImpl2 implements UserService{

    private UserDao userDao;
    String name;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl2() {}

    public UserServiceImpl2(String name, UserDao userDao) {
        this.name = name;
        this.userDao = userDao;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void hello() {
        userDao.say();
        System.out.println(name + "\tconstructor injection....");
    }
}
