package com.wil.service;

import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * Created by wil on 2017/12/8.
 */
@Service
public interface ProductService {

    List<String> findAllProductNames();
    void save(String name);
}
