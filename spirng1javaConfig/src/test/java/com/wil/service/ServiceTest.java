package com.wil.service;

import com.wil.BaseTest;
import com.wil.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wil on 2017/10/30.
 */

public class ServiceTest extends BaseTest {

    //动态产生的代理对象是目标对象的兄弟，类型必须是接口
    @Autowired
    private UserService userServiceIml;

    //动态产生代理对象是目标对象的子类
    @Autowired
    private AuthorService authorService;

    @Test
    public void save() {
        userServiceIml.save();
    }

    @Test
    public void count() {
        Long count = userServiceIml.count();
        System.out.println(count);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("exception");
        user.setAddress_id(3);
        userServiceIml.insert(user);
    }

}
