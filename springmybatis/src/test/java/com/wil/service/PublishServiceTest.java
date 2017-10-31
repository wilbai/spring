package com.wil.service;

import com.wil.entity.Publish;
import com.wil.service.impl.PublishServiceImpl;
import com.wil.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by wil on 2017/10/31.
 */
public class PublishServiceTest extends BaseTest {

    @Autowired
    private PublishServiceImpl publishService;

    @Test
    public void save() throws Exception {
        Publish publish = new Publish();
        publish.setPublishname("中国国际出版社");
        publish.setAddress("北京");
        publish.setPname("木奇");
        publish.setTel("010-77883344");
        publishService.save(publish);
        System.out.println(publish.getId());
    }

    @Test
    public void findById() throws Exception {
        Publish publish = publishService.findById(6);
        System.out.println(publish);
    }

}