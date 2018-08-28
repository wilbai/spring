package com.wil.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by wil on 2017/12/24.
 */
@Component
public class OrderProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void setOder() {
        jmsTemplate.send("order-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String message = "ticket order message";
                TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
            }
        });
    }


}
