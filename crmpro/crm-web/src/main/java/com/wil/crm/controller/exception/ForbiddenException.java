package com.wil.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 无权限403时
 * Created by wil on 2017/11/10.
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {}

    public ForbiddenException(String message) {
        super(message);
    }


}
