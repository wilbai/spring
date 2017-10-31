package com.wil.service.impl;

import com.wil.entity.Publish;
import com.wil.mapper.PublishMapper;
import com.wil.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wil on 2017/10/31.
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private PublishMapper publishMapper;

    @Override
    public int save(Publish publish) {
        return publishMapper.insert(publish);
    }

    @Override
    public Publish findById(Integer id) {
        System.out.println(publishMapper.getClass().getName());
        return publishMapper.selectByPrimaryKey(id);
    }
}
