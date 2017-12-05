package com.wil.mq;

import com.wil.entity.Product;
import com.wil.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by wil on 2017/12/5.
 */
//@Component
public class ProductInventoryConsumer {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryConsumer.class);

    @Autowired
    private ProductMapper productMapper;

    //@JmsListener(destination = "product_inventory_queue", containerFactory = "jmsListenerContainerFactory")
    public void job(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            Integer id = Integer.parseInt(text);

            logger.info("减库存开始--------");
            Product product = productMapper.selectByPrimaryKey(id);
            product.setProductInventory(product.getProductInventory() - 1);
            productMapper.updateByPrimaryKey(product);
            logger.info("减库存结束--------");

            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
