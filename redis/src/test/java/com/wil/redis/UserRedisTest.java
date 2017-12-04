package com.wil.redis;

import com.wil.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wil on 2017/12/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class UserRedisTest {

    private RedisTemplate<String, User> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
    }

    @Test
    public void saveUser() {
        User user = new User(5, "沐遥", "长安", "0088773");
        redisTemplate.opsForValue().set("user:5", user);
    }

    @Test
    public void getUser() {
        User user = redisTemplate.opsForValue().get("user:5");
        System.out.println(user);
    }
}
