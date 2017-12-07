package com.wil.service.impl;

import com.wil.entity.Consumer;
import com.wil.mapper.ConsumerMapper;
import com.wil.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wil on 2017/12/6.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;

    @Override
    public void login(String mobile, String password) {
        Consumer consumer = consumerMapper.findByMobile(mobile);
        if(consumer == null) {
            throw new RuntimeException("帐号不存在");
        } else {
            if(!password.equals(consumer.getMobile())) {
                throw new RuntimeException("帐号或用户名错误");
            }
        }
    }
}
