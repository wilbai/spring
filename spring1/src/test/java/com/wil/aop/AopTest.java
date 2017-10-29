package com.wil.aop;

import com.wil.service.BookService;
import com.wil.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wil on 2017/10/29.
 */
public class AopTest {

    @Test
    public void aopTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //必须是接口指向实现类，因为动态产生的是目标对象的兄弟，兄弟之间不能类型转换
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService.getClass().getName());
        userService.hello();

    }

    @Test
    public void extendsTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //父类指向子类对象
        BookService bookService = (BookService) context.getBean("bookService");
        System.out.println(bookService.getClass().getName());
        bookService.count();

    }
}
