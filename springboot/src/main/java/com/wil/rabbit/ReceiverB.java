package com.wil.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by wil on 2019/3/1.
 */
@Component
@RabbitListener(queues = "q_fanout_B")
public class ReceiverB {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("ReceiverB  : " + hello + "/n");
    }
}

