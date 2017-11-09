package com.wil.crm.service;

import com.wil.crm.entity.Customer;

import java.util.List;

/**
 * Created by wil on 2017/11/9.
 */
public interface CustomerService {

    List<Customer> findAllCustomer(Integer id);

    void saveCustomer(Customer customer);

    Customer findCustomerById(Integer id);

    void editCustomer(Customer customer);

    void drawCusToSea(Integer id);

    void delCustomer(Integer id);
}
