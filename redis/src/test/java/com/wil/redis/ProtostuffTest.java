package com.wil.redis;

import com.wil.entity.User;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
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
public class ProtostuffTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void saveUser() {

        User user = new User(4, "faker", "美国","67342");
        Jedis jedis = jedisPool.getResource();

        Schema<User> userSchema = RuntimeSchema.getSchema(User.class);
        byte[] bytes = ProtostuffIOUtil.toByteArray(user, userSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        jedis.set("user:4".getBytes(), bytes);
        jedis.close();

    }

    @Test
    public void getUser() {

        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get("user:4".getBytes());

        Schema<User> userSchema = RuntimeSchema.getSchema(User.class);
        User user = new User();
        ProtostuffIOUtil.mergeFrom(bytes,user,userSchema);
        System.out.println(user);

        jedis.close();


    }


}
