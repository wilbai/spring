package com.wil.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by wil on 2017/11/23.
 */
@Component
public class SpringAnnotationListener {

    @JmsListener(destination = "spring MQ queue")
    public void doSomething(String message) {
        System.out.println(">>>>>>>>>>>" + message);
    }

}
