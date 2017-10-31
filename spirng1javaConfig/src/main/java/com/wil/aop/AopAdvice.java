package com.wil.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wil on 2017/10/30.
 */
@Component//将通知类放入容器
//@Aspect//设置为通知类
public class AopAdvice {

    @Pointcut("execution(* com.wil.service..*.*(..))")
    public void pointcut() {}

    /*@Before("pointcut()")
    public void before() {
        System.out.println("前置通知");
    }

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("后置通知" + result);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        System.out.println("异常通知" + ex);
    }

    @After("pointcut()")
    public void after() {
        System.out.println("最终通知");
    }*/

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            System.out.println("--before--");
            result = joinPoint.proceed();
            System.out.println("--after--");

        } catch (Throwable throwable) {
            System.out.println("--throwing--" + throwable);
            throw new RuntimeException();
        } finally {
            System.out.println("--finally--");
        }
        return result;
    }


}
