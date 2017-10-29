package com.wil.proxy.jkd;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wil on 2017/10/29.
 */
public class MyInvocationHandler implements InvocationHandler{

    private Object target;
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("before....");
        Object result = method.invoke(target, args);
        System.out.println("after....");
        return result;
    }
}
