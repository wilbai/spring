package com.wil.proxyprogress;

/**
 * Created by wil on 2017/10/30.
 */
public class UserService implements Service {
    @Override
    public void save() {
        System.out.println("userService save.....");
    }

    @Override
    public void count() {
        System.out.println("userService count....");
    }
}
