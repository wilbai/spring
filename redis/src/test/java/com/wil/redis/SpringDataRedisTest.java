package com.wil.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wil on 2017/12/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class SpringDataRedisTest {

    private RedisTemplate<String, String> template;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.template = redisTemplate;
        this.template.setKeySerializer(new StringRedisSerializer());
        this.template.setValueSerializer(new StringRedisSerializer());
    }

    @Test
    public void setValue() {
        template.opsForValue().set("hobby", "football");
    }

    @Test
    public void getValue() {
        System.out.println(template.opsForValue().get("hobby"));

    }

    @Test
    public void setList() {
        //template.opsForList().leftPushAll("data-list", "aa", "bb", "cc");
        System.out.println(template.opsForList().rightPop("data-list"));
    }


}
