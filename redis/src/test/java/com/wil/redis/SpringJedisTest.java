package com.wil.redis;

import com.alibaba.fastjson.JSON;
import com.wil.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by wil on 2017/12/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class SpringJedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void setValue() {
        Jedis jedis = jedisPool.getResource();
        jedis.set("address", "shanghai");
        jedis.close();
    }

    @Test
    public void saveUser() {
        User user = new User(2, "baster", "beijing", "110110001");
        Jedis jedis = jedisPool.getResource();
        jedis.set("user:2", JSON.toJSONString(user));
        jedis.close();
    }

    @Test
    public void getUser() {
        Jedis jedis = jedisPool.getResource();
        User user = JSON.parseObject(jedis.get("user:2"), User.class);
        System.out.println(user);
    }





}
