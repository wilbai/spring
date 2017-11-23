package com.wil.mq;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by wil on 2017/11/23.
 */
@Component
public class MyQueueListener implements SessionAwareMessageListener {
    /*@Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("from MQ-queue " + textMessage.getText());
            if(1 == 1) {
                throw new JMSException("on purpose exception...");
            }
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            session.recover();
        }
    }
}
