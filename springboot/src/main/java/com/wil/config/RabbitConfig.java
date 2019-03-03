package com.wil.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wil on 2019/3/1.
 * 简单模式
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("q_hello");
    }
}
