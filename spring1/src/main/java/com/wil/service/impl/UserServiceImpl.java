package com.wil.service.impl;

import com.wil.dao.UserDao;
import com.wil.service.UserService;
import javafx.scene.effect.SepiaTone;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wil on 2017/10/28.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private Integer id;
    private String name;
    private List<String> nameList;
    private Set<UserDao> userDaoSet;
    private Map<String, UserDao> userDaoMap;
    private Properties properties;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setUserDaoSet(Set<UserDao> userDaoSet) {
        this.userDaoSet = userDaoSet;
    }

    public void setUserDaoMap(Map<String, UserDao> userDaoMap) {
        this.userDaoMap = userDaoMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "UserServiceImpl{" +
                "userDao=" + userDao +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", nameList=" + nameList +
                ", userDaoSet=" + userDaoSet +
                ", userDaoMap=" + userDaoMap +
                ", properties=" + properties +
                '}';
    }

    @Override
    public void hello() {
        userDao.say();
        System.out.println("userService:hello, spring...");
        System.out.println(id + name + nameList + userDaoSet + userDaoMap + properties);
    }
}
