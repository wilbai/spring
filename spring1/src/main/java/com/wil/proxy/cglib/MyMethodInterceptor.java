package com.wil.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wil on 2017/10/29.
 */
public class MyMethodInterceptor implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method,
                            Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before....");
        Object result = methodProxy.invokeSuper(o,objects);
        System.out.println("after....");

        return result;
    }
}
