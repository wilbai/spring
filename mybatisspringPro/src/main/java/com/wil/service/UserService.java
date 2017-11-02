package com.wil.service;

import com.wil.entity.User;

import java.util.List;

/**
 * Created by wil on 2017/11/2.
 */
public interface UserService {

    User findById(int id);
    User findByName(String name);
    List<User> findAll();
    void save(User user);
    void delete(int id);
    void update(User user);
}
