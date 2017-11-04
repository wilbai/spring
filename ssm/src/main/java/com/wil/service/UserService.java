package com.wil.service;

import com.wil.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wil on 2017/11/2.
 */
public interface UserService {

    User findById(int id);
    List<User> findByName(String name);
    List<User> findAll();
    List<User> findByPageNo(Integer pageNo);
    void save(User user);
    void delete(int id);
    void update(User user);
}
