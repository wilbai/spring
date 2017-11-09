package com.wil.crm.service.impl;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.example.CustomerExample;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.mapper.CustomerMapper;
import com.wil.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wil on 2017/11/9.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> findAllCustomer(Integer id) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(id);
        customerExample.setOrderByClause("id desc");
        return customerMapper.selectByExample(customerExample);
    }

    @Override
    public void saveCustomer(Customer customer) throws ServiceException {
        String mobile = customer.getMobile();
        Customer customer1 = customerMapper.findByMobile(mobile);
        if(customer1 != null) {
            throw new ServiceException("手机号已被使用，请查看是否输入有误");
        }
        customer.setLastContactTime(new Date());
        customerMapper.insertSelective(customer);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editCustomer(Customer customer) {
        String mobile = customer.getMobile();
        Customer customer1 = customerMapper.findByMobile(mobile);
        if(customer1 != null) {
            throw new ServiceException("手机号已被使用，请查看是否输入有误");
        }
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public void drawCusToSea(Integer id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        customer.setAccountId(0);
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public void delCustomer(Integer id) {
        customerMapper.deleteByPrimaryKey(id);
    }
}
