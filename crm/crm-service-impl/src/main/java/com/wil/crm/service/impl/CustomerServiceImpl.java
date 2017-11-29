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
import org.apache.commons.io.IOUtils;
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
        Account account = accountMapper.selectByPrimaryKey(accountId);
        customer.setAccountId(accountId);
        String record = customer.getRecord() == null ? "" : customer.getRecord() + ";";
        customer.setRecord(record + "该客户从同事："+account.getUserName()+"("+account.getMobile()+")"+"处转交过来");
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
    public void exportDataToCsvFile(Account account, OutputStream outputStream) throws IOException {
        List<Customer> customerList = findCustomerListByAccountId(account);

        StringBuilder builder = new StringBuilder();
        builder.append("姓名").append(",").append("联系方式").append(",")
                .append("职位").append(",").append("地址").append("\r\n");
        for(Customer customer : customerList) {
            builder.append(customer.getCustomerName()).append(",")
                    .append(customer.getMobile()).append(",")
                    .append(customer.getJobTitle()).append(",")
                    .append(customer.getAddress()).append("\r\n");
        }
        IOUtils.write(builder.toString(), outputStream, "GBK");
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 将客户信息导出为xls文件
     * @param account
     * @param outputStream
     */
    @Override
    public void exportDataToXlsFile(Account account, OutputStream outputStream) throws IOException {
        List<Customer> customerList = findCustomerListByAccountId(account);
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("我的客户");
        Row titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("姓名");
        titleRow.createCell(1).setCellValue("联系电话");
        titleRow.createCell(2).setCellValue("职位");
        titleRow.createCell(3).setCellValue("地址");

        for(int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(customer.getCustomerName());
            dataRow.createCell(1).setCellValue(customer.getMobile());
            dataRow.createCell(2).setCellValue(customer.getJobTitle());
            dataRow.createCell(3).setCellValue(customer.getAddress());
        }

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

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


}
