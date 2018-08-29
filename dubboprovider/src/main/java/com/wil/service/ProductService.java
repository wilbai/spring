package com.wil.service;

import java.util.List;

/**
 * Created by wil on 2017/12/8.
 */
public interface ProductService {

    List<String> findAllProductNames();
    void save(String name);
}
