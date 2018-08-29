package com.wil.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wil.service.ProductService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wil on 2017/12/8.
 */
@Service(timeout = 5000,version = "1.2")
public class ProductServiceImpl implements ProductService{
    public List<String> findAllProductNames() {
        return Arrays.asList("充电宝","数据线","手机","音响");
    }

    public void save(String name) {
        System.out.println("saved -> " + name);
    }
}
