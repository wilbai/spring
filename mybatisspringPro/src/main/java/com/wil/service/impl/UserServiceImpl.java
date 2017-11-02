package com.wil.service.impl;

import com.wil.entity.User;
import com.wil.entity.UserExample;
import com.wil.mapper.UserMapper;
import com.wil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wil on 2017/11/2.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findByName(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        return (User) userMapper.selectByExample(userExample);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
