package com.wil.proxy;

import com.wil.proxy.jkd.MyInvocationHandler;
import com.wil.service.StudentService;
import com.wil.service.impl.StudentServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by wil on 2017/10/29.
 */
public class JdkProxyTest {

    @Test
    public void userServiceProxy() {
        //目标对象
        StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
        //代理对象
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(studentServiceImpl);

        //目标对象的接口指向实现类（动态产生的）
        // 参数：loader,interface,代理对象
        StudentService studentService = (StudentService) Proxy.newProxyInstance(
                studentServiceImpl.getClass().getClassLoader(),
                studentServiceImpl.getClass().getInterfaces(),
                myInvocationHandler);
        System.out.println(studentService.getClass().getName());
        studentService.save();

    }


}
