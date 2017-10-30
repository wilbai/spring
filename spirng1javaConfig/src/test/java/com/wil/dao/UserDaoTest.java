package com.wil.dao;

import com.wil.Application;
import com.wil.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wil on 2017/10/30.
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        userDao.save();
    }

    @Test
    public void load() {
        System.out.println(userDao.count());
    }


}
