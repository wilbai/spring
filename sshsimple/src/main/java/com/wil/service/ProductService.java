package com.wil.service;

import com.wil.dao.ProductDao;
import com.wil.dao.ProductTypeDao;
import com.wil.pojo.Product;
import com.wil.pojo.ProductType;
import com.wil.util.Page;
import com.wil.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wil on 2017/11/29.
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTypeDao productTypeDao;

    public List<Product> findAll() {
        return productDao.findByPage(0, 50);
    }


    public void saveProduct(Product product) {
        productDao.saveOrUpdate(product);
    }

    public void editProduct(Product product) {
        productDao.update(product);
    }

    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    public void deleteById(Integer id) {
        Product product = productDao.findById(id);
        productDao.delete(product);
    }

    public List<Product> findByRequestQuery(List<RequestQuery> queryList) {
        if(queryList == null || queryList.size() == 0) {
            return findAll();
        }
        return productDao.findByRequestQueryList(queryList);
    }


    public Page<Product> findByRequestQuery(List<RequestQuery> queryList, Integer pageNo) {
        return productDao.findByRequestQueryListAndPageNo(queryList, pageNo);
    }

    public List<ProductType> findAllType() {
        return productTypeDao.findByPage(0,5);
    }

    public ProductType findTypeById(Integer id) {
        return productTypeDao.findById(id);
    }
}
