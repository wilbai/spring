package com.wil.proxyprogress.jdkpro;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wil on 2017/10/30.
 */
public class MyJdkProxy implements InvocationHandler {

    private Object target;
    public MyJdkProxy(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before....");

        Object result = method.invoke(target, args);
        System.out.println(proxy.getClass().getName());
        return result;
    }
}
