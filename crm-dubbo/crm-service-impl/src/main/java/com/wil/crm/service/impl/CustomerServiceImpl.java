package com.wil.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.entity.Task;
import com.wil.crm.example.CustomerExample;
import com.wil.crm.example.SaleChanceExample;
import com.wil.crm.example.TaskExample;
import com.wil.crm.mapper.AccountMapper;
import com.wil.crm.mapper.CustomerMapper;
import com.wil.crm.mapper.SaleChanceMapper;
import com.wil.crm.mapper.TaskMapper;
import com.wil.crm.service.CustomerService;
import com.wil.crm.service.SaleChanceService;
import com.wil.crm.service.TaskService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户管理业务层
 * Created by wil on 2017/11/10.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    TaskService taskService;

    @Value("#{'${customer.source}'.split(',')}")
    private List<String> customerSource;
    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> customerTrade;

    /**
     * 查找我的客户列表并分页
     * @param pageNo
     * @param account
     * @return
     */
    @Override
    public PageInfo<Customer> pageForMyCustomer(Integer pageNo, Account account) {
        PageHelper.startPage(pageNo, 5);
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());
        customerExample.setOrderByClause("id desc");
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        return new PageInfo<Customer>(customerList);
    }

    /**
     * 获取所有的客户来源
     * @return
     */
    public List<String> findAllCusSource() {
        return customerSource;
    }

    /**
     * 获取所有客户行业
     * @return
     */
    public List<String> findAllCusTrade() {
        return customerTrade;
    }

    /**
     * 添加新客户
     * @param customer
     */
    @Override
    public void saveCustomer(Customer customer) {
        customer.setLastContactTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insertSelective(customer);
        logger.info("添加新客户:{}",customer.getCustomerName());
    }

    /**
     * 根据ID查找客户
     * @param id
     * @return
     */
    @Override
    public Customer findCusById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改客户资料
     * @param customer
     */
    @Override
    public void editCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 转交客户
     * @param customer
     * @param accountId
     */
    @Override
    public void transCustomer(Customer customer, Integer accountId) {
        Account oldAccount = accountMapper.selectByPrimaryKey(customer.getAccountId());
        customer.setAccountId(accountId);
        customer.setLastContactTime(new Date());
        String record = customer.getRecord() == null ? "" : customer.getRecord() + ";";
        customer.setRecord(record + "该客户从同事："+oldAccount.getUserName()+"("+oldAccount.getMobile()+")"+"处转交过来");
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 将客户放入公海
     * @param customer
     */
    @Override
    public void publicCustomer(Customer customer) {
        Account account = accountMapper.selectByPrimaryKey(customer.getAccountId());
        customer.setAccountId(0);
        customer.setLastContactTime(new Date());
        String record = customer.getRecord() == null ? "" : customer.getRecord() + ";";
        customer.setRecord(record + account.getUserName() + "将该客户放入公海");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 删除客户
     * @param customer
     */
    @Override
    public void deleteCustomer(Customer customer) {
        //检查是否有关联的交易记录
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustomerIdEqualTo(customer.getId());
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        if(saleChanceList != null && saleChanceList.size() > 0) {
            for(SaleChance saleChance : saleChanceList) {
                saleChanceService.deleteSaleChanceById(saleChance.getId());
            }
        }

        //检查是否有关联的待办事项
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andCustomerIdEqualTo(customer.getId());
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        if(taskList != null && taskList.size() > 0) {
           for(Task task : taskList) {
               taskService.deleteTaskById(task.getId());
           }
        }

        customerMapper.deleteByPrimaryKey(customer.getId());
        logger.info("删除了客户:{}", customer.getCustomerName());
    }

    /**
     * 将客户信息导出为csv文件
     *
     * @param account
     * @param outputStream
     */
    @Override
    public void exportDataToCsvFile(Account account, OutputStream outputStream) throws IOException {}

    /**
     * 将客户信息导出为xls文件
     * @param account
     * @param outputStream
     */
    @Override
    public void exportDataToXlsFile(Account account, OutputStream outputStream) throws IOException {}

    /**
     * 根据accountId查找客户列表
     * @param account
     * @return
     */
    public List<Customer> findCustomerListByAccountId(Account account) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return customerList;
    }

    /**
     * 根据等级将客户分组
     * @param account
     * @return 返回每组的级别和数量
     */
    @Override
    public List<Map<String, Object>> countCustomerByLevel(Account account) {
        return customerMapper.countByLevel(account.getId());
    }

    /**
     * 根据创建时间分组
     * @param account
     * @return 返回每组的月份和数量
     */
    @Override
    public List<Map<String, Object>> countCustomerByCreateTime(Account account) {
        return customerMapper.countByMonthly(account.getId());
    }

    @Override
    public PageInfo<Customer> pageForPublicCustomer(Integer pageNo) {
        PageHelper.startPage(pageNo, 5);
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(0);
        customerExample.setOrderByClause("id desc");
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return new PageInfo<Customer>(customerList);
    }

    /**
     * 当前account从公海接收客户
     * @param customer
     * @param account
     */
    @Override
    public void getCusFromPub(Customer customer, Account account) {
        customer.setAccountId(account.getId());
        String record = customer.getRecord() == null ? "" : customer.getRecord() + ";";
        customer.setRecord(record + account.getUserName() + "从公海接收客户");
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKey(customer);

    }


}
