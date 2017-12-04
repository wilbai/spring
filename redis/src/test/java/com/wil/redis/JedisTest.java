package com.wil.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by wil on 2017/12/4.
 */
public class JedisTest {

    @Test
    public void setValue() {
        Jedis jedis = new Jedis("192.168.84.130", 6379);
        jedis.set("name", "seber");
        jedis.close();
    }

    @Test
    public void getValue() {
        Jedis jedis = new Jedis("192.168.84.130", 6379);
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    @Test
    public void poolTest() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(10);
        config.setMinIdle(5);
        JedisPool jedisPool = new JedisPool(config, "192.168.84.130",6379);

        Jedis jedis = jedisPool.getResource();
        jedis.hset("user:1", "score:english", "100");
        System.out.println(jedis.hget("user:1", "score:math"));
        jedis.close();
        jedisPool.destroy();
    }

}
