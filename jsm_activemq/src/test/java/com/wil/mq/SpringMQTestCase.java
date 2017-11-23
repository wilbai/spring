package com.wil.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by wil on 2017/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms.xml")
public class SpringMQTestCase {

    @Autowired
    private SpringMQProducer springMQProducer;

    @Test
    public void sendMessage() {
        for(int i = 0; i < 4; i++) {
            springMQProducer.sendMessage("hello,spring-Mq topic-" + i);
        }
    }


}
