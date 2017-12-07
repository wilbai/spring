package com.wil.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;

/**
 * Created by wil on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class JedisClusterSpringTest {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void get() throws IOException {
        System.out.println(jedisCluster.get("name"));
        jedisCluster.close();
    }



}
