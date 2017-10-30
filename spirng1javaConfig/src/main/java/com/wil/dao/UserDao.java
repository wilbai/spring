package com.wil.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by wil on 2017/10/30.
 */
@Repository
public class UserDao {

    public void save() {
        System.out.println("userDAo save....");
    }

    public int count() {
        return 100;
    }
}
