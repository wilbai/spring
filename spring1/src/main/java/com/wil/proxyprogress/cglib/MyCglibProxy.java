package com.wil.proxyprogress.cglib;

import com.wil.proxyprogress.jdkpro.NodeService;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by wil on 2017/10/30.
 */
public class MyCglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("interceptor after....");
        return result;
    }
}
