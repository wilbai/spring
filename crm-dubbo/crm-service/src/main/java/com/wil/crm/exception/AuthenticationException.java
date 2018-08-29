package com.wil.crm.exception;

/**
 * 帐号登录时错误抛出该异常
 * Created by wil on 2017/11/7.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(){}

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable th) {
        super(th);
    }

    public AuthenticationException(Throwable th,String message) {
        super(message,th);
    }

}
