package com.wil.mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;


/**
 * Created by wil on 2017/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms.xml")
public class SpringMQTestCase {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void sendQueueMessage() throws IOException {
        jmsTemplate.send("spring-MQ-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello queue message...");
            }
        });
        //必须写 否则看不到重试机制程序就停了
        System.in.read();
    }

    @Test
    public void sendTopicMessage() throws IOException {
        ActiveMQTopic destination = new ActiveMQTopic("spring-MQ-topic");
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("hello topic message...");
            }
        });
        System.in.read();
    }


}
