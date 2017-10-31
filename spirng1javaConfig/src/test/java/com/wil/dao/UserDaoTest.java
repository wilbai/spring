package com.wil.dao;

import com.wil.BaseTest;
import com.wil.service.AuthorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wil on 2017/10/30.
 */
public class UserDaoTest extends BaseTest{

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorService authorService;

    @Test
    public void save() {
        userDao.save();
    }

    @Test
    public void load() {
        System.out.println(userDao.count());
    }

    @Test
    public void AuthorTest() {
        authorService.save();
    }

}
