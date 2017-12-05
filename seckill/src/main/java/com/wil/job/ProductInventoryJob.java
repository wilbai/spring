package com.wil.job;

import com.wil.entity.Product;
import com.wil.mapper.ProductMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by wil on 2017/12/5.
 */

public class ProductInventoryJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String productId = jobDataMap.getString("productId");

        try {
            ApplicationContext context = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");
            ProductMapper productMapper = (ProductMapper) context.getBean("productMapper");
            JedisPool jedisPool = (JedisPool) context.getBean("jedisPool");

            Jedis jedis = jedisPool.getResource();
            Long size = jedis.llen("product:" + productId + ":inventory");

            Product product = productMapper.selectByPrimaryKey(Integer.parseInt(productId));
            product.setProductInventory(size.intValue());
            productMapper.updateByPrimaryKey(product);

            jedis.close();
            logger.info("商品:{} 将库存修改为:{}",productId, size);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
