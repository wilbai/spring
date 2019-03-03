package com.wil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wil on 2018/11/8.
 * 为了使用默认的ExecutorService
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService getThreaPool() {
        return Executors.newFixedThreadPool(10);
    }
}
