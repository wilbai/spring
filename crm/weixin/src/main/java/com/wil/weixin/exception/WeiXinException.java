package com.wil.weixin.exception;


/**
 * Created by wil on 2017/11/20.
 */
public class WeiXinException extends RuntimeException {

    public WeiXinException() {}

    public WeiXinException(String message) {
        super(message);
    }

    public WeiXinException(String message, Exception e) {
        super(message, e);
    }



}
