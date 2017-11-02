package com.wil.service.impl;

import com.wil.entity.User;
import com.wil.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wil on 2017/11/2.
 */
public class UserServiceImplTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void findById() throws Exception {

        User user = userService.findById(3);
        System.out.println(user);

    }

    @Test
    public void findByName() {
        User user = userService.findByName("archer");
        System.out.println(user);
    }

    @Test
    public void findAll() {
        List<User> userList = userService.findAll();
        for(User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void delete() {
        userService.delete(17);
    }

    @Test
    public void save() throws Exception {

        User user = new User();
        user.setName("archer");
        user.setAddressId(4);
        userService.save(user);
    }

}