package com.wil.dao;

import com.wil.pojo.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wil on 2017/11/29.
 */
@Repository
public class ProductDao extends BaseDao<Product, Integer>{



}
