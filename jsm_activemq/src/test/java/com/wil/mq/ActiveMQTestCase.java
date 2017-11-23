package com.wil.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by wil on 2017/11/22.
 */
public class ActiveMQTestCase {

    @Test
    public void sendMessageToQueue() throws JMSException {
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        Destination destination = session.createTopic("Topic Message");

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        TextMessage textMessage = session.createTextMessage("hello, MQ-6");

        producer.send(textMessage);
        session.commit();

        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void getMessageFromQueue() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createTopic("Topic Message");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }


}
