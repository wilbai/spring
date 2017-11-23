package com.wil.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by wil on 2017/11/23.
 */
@Component
public class MyAnnotationListener {

    /*@JmsListener(destination = "spring-MQ-queue", containerFactory = "queueListenerContainerFactory")
    public void getMessageFromQueue(String message) {
        System.out.println("from queue message----->" + message);
    }*/

    @JmsListener(destination = "spring-MQ-topic", containerFactory = "topicListenerContainerFactory")
    public void getMessageFromTopic(String message) {
        System.out.println("from topic message----->" + message);
    }

    @JmsListener(destination = "spring-MQ-queue", containerFactory = "queueListenerContainerFactory")
    public void getMessageFromQueueWithRecover(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            if(1 == 1) {
                throw new JMSException("on purpose exception");
            }
            System.out.println(textMessage.getText());
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }

}
