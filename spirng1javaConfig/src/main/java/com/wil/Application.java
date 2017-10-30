package com.wil;

import com.wil.dao.UserDao;
import com.wil.service.AuthorService;
import com.wil.service.UserService;
import com.wil.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by wil on 2017/10/30.
 */
@Configuration//替代基础xml
@ComponentScan//DI
@EnableAspectJAutoProxy//AOP
public class Application {


    @Bean
    public UserService userService() {
       UserServiceImpl userService = new UserServiceImpl();
       userService.setUserDao(new UserDao());
       return userService;

    }



}
