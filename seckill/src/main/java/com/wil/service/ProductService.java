package com.wil.service;

import com.wil.entity.Product;

import java.io.InputStream;
import java.util.List;

/**
 * Created by wil on 2017/12/5.
 */
public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    void saveProduct(Product product, InputStream inputStream);

    void seckill(Integer id);
}
