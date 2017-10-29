package com.wil.proxy;

import com.wil.dao.UserDao;
import com.wil.proxy.cglib.MyMethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

/**
 * Created by wil on 2017/10/29.
 */
public class CGLibProxyTest {

    @Test
    public void proxy() {
        Enhancer enhancer = new Enhancer();
        //设置目标对象
        enhancer.setSuperclass(UserDao.class);
        //设置代理对象
        enhancer.setCallback(new MyMethodInterceptor());

        //产生目标对象的子类
        UserDao userDao = (UserDao) enhancer.create();
        System.out.println(userDao.getClass().getName());
        userDao.say();

    }

}
