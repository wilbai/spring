package com.wil.dao;

import com.wil.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wil on 2017/10/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)//(locations = "classpath:applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void save() {
        userDao.save();
    }

    @Test
    public void load() {

        userDao.save();

    }


}
