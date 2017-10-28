package com.wil.spring;

import com.wil.dao.UserDao;
import com.wil.service.UserService;
import com.wil.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wil on 2017/10/28.
 */
public class AccessSpringTest {

    @Test
    public void say() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserServiceImpl) context.getBean("userService");
        userService.hello();
    }

}
