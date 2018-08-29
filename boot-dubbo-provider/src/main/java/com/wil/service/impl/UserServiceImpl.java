package com.wil.service.impl;

import com.wil.service.UserService;

/**
 * Created by wil on 2017/12/11.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void sayHello(String name) {
        System.out.println(">>>>>>>>>>>>>>hello: " + name);
    }
}
