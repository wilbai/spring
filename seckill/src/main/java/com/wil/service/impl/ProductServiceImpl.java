package com.wil.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wil.entity.Product;
import com.wil.entity.ProductExample;
import com.wil.job.ProductInventoryJob;
import com.wil.mapper.ProductMapper;
import com.wil.service.ProductService;
import com.wil.service.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wil on 2017/12/5.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Value("${qiniu.ak}")
    private String qiniuAK;
    @Value("${qiniu.sk}")
    private String qiniuSK;
    @Value("${qiniu.buket}")
    private String qiniuBuket;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public List<Product> findAll() {
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("start_time asc");
        return productMapper.selectByExample(productExample);
    }

    /**
     * 根据id查找product 第一次时放入redis做为缓存
     * @param id
     * @return
     */
    @Override
    public Product findById(Integer id) {
        Product product;
        try(Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get("product:"+id);
            if(json == null) {
                product = productMapper.selectByPrimaryKey(id);
                jedis.set("product:"+id, JSON.toJSONString(product));
            } else {
                product = JSON.parseObject(json, Product.class);
            }
        }
        return product;
    }

    /**
     * 添加商品
     * @param product
     * @param inputStream
     */
    @Override
    @Transactional
    public void saveProduct(Product product, InputStream inputStream) {
        //图片上传到七牛
        String key = uploadToQiNiu(inputStream);
        product.setProductImage(key);
        //保存product对象
        productMapper.insertSelective(product);
        //在redis中添加商品库存数量的计数集合
        try(Jedis jedis = jedisPool.getResource()) {
            for(int i = 0; i < product.getProductInventory(); i++) {
                jedis.lpush("product:"+product.getId()+":inventory", String.valueOf(i));
            }
        }
        //添加秒杀结束时的定时任务
        addScheduleJob(product.getEndTime().getTime(), product.getId());

    }

    private void addScheduleJob(long endTime, Integer productId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAsString("productId", productId);

        JobDetail jobDetail = JobBuilder
                .newJob(ProductInventoryJob.class)
                .setJobData(jobDataMap)
                .withIdentity(new JobKey("productId:"+productId,"productInventoryGroup"))
                .build();

        DateTime dateTime = new DateTime(endTime);

        StringBuilder cron = new StringBuilder("0")
                .append(" ")
                .append(dateTime.getMinuteOfHour())
                .append(" ")
                .append(dateTime.getHourOfDay())
                .append(" ")
                .append(dateTime.getDayOfMonth())
                .append(" ")
                .append(dateTime.getMonthOfYear())
                .append(" ? ")
                .append(dateTime.getYear());

        logger.info("CRON EX: {}" ,cron.toString());

        ScheduleBuilder scheduleBuilder =
                CronScheduleBuilder.cronSchedule(cron.toString());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder)
                .build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new ServiceException(e, "添加定时任务异常");
        }

    }

    @Override
    public void seckill(Integer id) throws ServiceException {
        try(Jedis jedis = jedisPool.getResource()) {
            Product product = JSON.parseObject(jedis.get("product:"+id), Product.class);
            if(!product.isStart()) {
                throw new ServiceException("秒杀还没开始,请稍安勿躁,小心封号");
            }

            String value = jedis.lpop("product:"+id+":inventory");
            if(value == null) {
                logger.error("库存不足，商品秒杀失败");
                throw new ServiceException("商品被秒光");
            } else {
                logger.info("秒杀商品成功");
                //修改redis缓存中的商品库存
                product.setProductInventory(product.getProductInventory() - 1);
                jedis.set("product:"+id,JSON.toJSONString(product));

                /*jmsTemplate.send("product_inventory_queue", new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage textMessage = session.createTextMessage();
                        textMessage.setText(id.toString());
                        return textMessage;
                    }
                });*/
            }
        }
    }

    /**
     * 上传文件到七牛
     * @param inputStream
     * @return
     */
    private String uploadToQiNiu(InputStream inputStream) {
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(qiniuAK, qiniuSK);
        String uploadToken = auth.uploadToken(qiniuBuket);

        try {
            Response response = uploadManager.put(IOUtils.toByteArray(inputStream), null, uploadToken);
            DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return defaultPutRet.key;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传图片到七牛异常",e);
        }
    }


}
