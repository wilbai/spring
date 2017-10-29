package com.wil.aop;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by wil on 2017/10/29.
 */
public class AopAdvice {

    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    public void afterReturning(Object result) {
        System.out.println("后置通知>>>>" + result);
    }

    public void afterThrowing(Throwable ex) {
        System.out.println("异常通知>>>>>" + ex);
    }

    public void after() {
        System.out.println("最终通知");
    }

    public void around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            System.out.println("前置...");
            result = joinPoint.proceed();
            System.out.println("后置...");
        } catch (Throwable throwable) {
            System.out.println("异常....'");
        } finally {
            System.out.println("最终....");
        }
    }



}
