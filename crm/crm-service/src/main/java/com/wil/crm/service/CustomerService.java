package com.wil.crm.service;

import com.github.pagehelper.PageInfo;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 客户管理业务层
 * Created by wil on 2017/11/10.
 */
public interface CustomerService {

    /**
     * 查找我的客户列表并分页
     * @param pageNo
     * @param account
     * @return
     */
    PageInfo<Customer> pageForMyCustomer(Integer pageNo, Account account);

    /**
     * 获取所有的客户来源
     * @return
     */
    List<String> findAllCusSource();

    /**
     * 获取所有客户行业
     * @return
     */
    List<String> findAllCusTrade();

    /**
     * 添加新客户
     * @param customer
     */
    void saveCustomer(Customer customer);

    /**
     * 根据ID查找客户
     * @param id
     * @return
     */
    Customer findCusById(Integer id);

    /**
     * 修改客户资料
     * @param customer
     */
    void editCustomer(Customer customer);

    /**
     * 转交客户
     * @param customer
     * @param accountId
     */
    void transCustomer(Customer customer, Integer accountId);

    /**
     * 将客户放入公海
     * @param customer
     */
    void publicCustomer(Customer customer);

    /**
     * 删除客户
     * @param customer
     */
    void deleteCustomer(Customer customer);

    /**
     * 将客户信息导出为csv文件
     * @param account
     * @param outputStream
     */
    void exportDataToCsvFile(Account account, OutputStream outputStream) throws IOException;

    /**
     * 将客户信息导出为xls文件
     * @param account
     * @param outputStream
     */
    void exportDataToXlsFile(Account account, OutputStream outputStream) throws IOException;

    /**
     * 根据accountId查找customerList
     * @param account
     * @return
     */
    List<Customer> findCustomerListByAccountId(Account account);

    /**
     * 根据等级统计客户数量
     * @param account
     * @return
     */
    List<Map<String, Object>> countCustomerByLevel(Account account);

    /**
     * 统计每月新增客户数量
     * @param account
     * @return
     */
    List<Map<String,Object>> countCustomerByCreateTime(Account account);

    /**
     * 公海客户list
     * @param pageNo
     * @return
     */
    PageInfo<Customer> pageForPublicCustomer(Integer pageNo);

    /**
     *当前account从公海接收客户
     * @param id
     * @param account
     */
    void getCusFromPub(Customer customer, Account account);
}
