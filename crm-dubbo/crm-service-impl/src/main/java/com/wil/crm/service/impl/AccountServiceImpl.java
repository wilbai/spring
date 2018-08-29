package com.wil.crm.service.impl;

import com.wil.crm.entity.*;
import com.wil.crm.example.*;
import com.wil.crm.exception.AuthenticationException;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.mapper.*;
import com.wil.crm.service.AccountService;
import com.wil.crm.service.CustomerService;
import com.wil.crm.service.SaleChanceService;
import com.wil.crm.service.TaskService;
import com.wil.weixin.WeiXinUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/7.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final static Integer COMPANY_ID = 1;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private AccountDeptMapper accountDeptMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private WeiXinUtil weiXinUtil;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DiskMapper diskMapper;
    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskService taskService;

    @Override
    public Account login(String mobile, String password) {
        Account account = accountMapper.findByMobile(mobile);
        String salt = "mysql";
        String codePass = DigestUtils.md5Hex(salt + password);
        if(account != null && codePass.equals(account.getPassword())) {
            logger.info("{} 在 {} 登录成功",account.getUserName(), new Date());
            return account;
        } else {
            throw new AuthenticationException("用户名或密码错误");
        }

    }

    @Override
    @Transactional
    public void saveDept(String deptName) throws ServiceException {
        Dept dept = deptMapper.findByName(deptName);
        if (dept != null) {
            throw new ServiceException("部门已存在");
        } else {
            dept = new Dept();
            dept.setDeptName(deptName);
            dept.setpId(COMPANY_ID);
            deptMapper.insertSelective(dept);

            //添加到微信
            weiXinUtil.createDept(dept.getId(), COMPANY_ID, deptName);

            logger.info("添加新部门:{}",deptName);
        }
    }

    @Override
    public List<Dept> findAllDept() {
        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 根据参数查找分页后的list
     * @param map
     * @return
     */
    @Override
    public List<Account> pageByParam(Map<String, Object> map) {

        Integer start = (Integer) map.get("start");
        Integer length = (Integer) map.get("length");
        Integer deptId = (Integer) map.get("deptId");
        String accountName = (String) map.get("accountName");

        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }

        return accountMapper.findPageByParam(start, length, deptId, accountName);
    }

    /**
     * 根据deptId查找account的总数
     *
     * @param deptId
     * @return
     */
    @Override
    public Long countByDeptId(Integer deptId) {

        return accountMapper.countByDeptId(deptId);
    }

    /**
     * 添加新员工
     * @param userName
     * @param mobile
     * @param password 密码明文接收
     * @param deptIdArray 部门可以多选
     */
    @Override
    @Transactional
    public void saveEmployee(String userName, String mobile, String password, Integer[] deptIdArray) throws ServiceException {
        //验证手机号唯一
        Account account = accountMapper.findByMobile(mobile);
        if(account != null) {
            throw new ServiceException("手机号已被使用，请查看是否输入有误");
        }
        //保存
        account = new Account();
        account.setUserName(userName);
        account.setMobile(mobile);
        String salt = "mysql";
        account.setPassword(DigestUtils.md5Hex(salt + password));
        accountMapper.insertSelective(account);
        //添加关联关系
        insertAccountDept(deptIdArray, account);
        //添加员工到微信
        weiXinUtil.createAccount(account.getId(), userName, Arrays.asList(deptIdArray), mobile);

        logger.info("添加新员工：{}, 时间：{}",userName, new Date());
    }

    /**
     * 根据id删除员工
     * @param id
     */
    @Override
    @Transactional
    public void delEmployeeById(Integer id) throws ServiceException {
        //判断客户关系，将客户放入公海
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(id);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        if(customerList != null && customerList.size() > 0) {
            for(Customer customer : customerList) {
                customerService.publicCustomer(customer);
            }
        }
        //修改该员工上传文件的accountId为0
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andAccountIdEqualTo(id);
        List<Disk> diskList = diskMapper.selectByExample(diskExample);
        if(diskList != null && diskList.size() > 0 ) {
            for(Disk disk : diskList) {
                disk.setAccountId(0);
                diskMapper.updateByPrimaryKeySelective(disk);
            }
        }

        //删除所有的销售机会
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andAccountIdEqualTo(id);
        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
        if(saleChanceList != null && saleChanceList.size() > 0) {
            for(SaleChance saleChance : saleChanceList) {
                saleChanceService.deleteSaleChanceById(saleChance.getId());
            }
        }

        //删除所有的提醒任务
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(id);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        if(taskList != null && taskList.size() > 0) {
            for(Task task : taskList) {
                taskService.deleteTaskById(task.getId());
            }
        }

        //删除与部门的关联关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
        //删除员工
        accountMapper.deleteByPrimaryKey(id);
        weiXinUtil.deleteAccount(id);
        logger.info("删除ID为:{}的员工",id);
    }

    @Override
    public List<Account> findAllAccount() {
        return accountMapper.selectByExample(new AccountExample());
    }

    /**
     * 根据id删除部门
     * @param id
     */
    @Override
    @Transactional
    public void delDeptById(Integer id) {
        Dept dept = deptMapper.selectByPrimaryKey(id);
        if(dept == null) {
            throw new ServiceException("部门不存在或已被删除");
        }
        //判断部门是否有有员工
        Long accountNum = accountMapper.countAccountByDeptId(id);
        if(accountNum == 0) {
            deptMapper.deleteByPrimaryKey(id);
            //删除微信中的部门
            weiXinUtil.deleteDept(id);
            logger.info("删除ID为:{}的{}部门", id, dept.getDeptName());
        } else {
            throw new ServiceException("部门中含有员工,不能删除");
        }
    }

    @Override
    public void editDept(Integer id, String deptName) {

        Dept dept = deptMapper.findByName(deptName);
        if (dept != null) {
            throw new ServiceException("部门已存在");
        } else {
            dept = deptMapper.selectByPrimaryKey(id);
            dept.setDeptName(deptName);
            deptMapper.updateByPrimaryKey(dept);
            //修改微信中的部门
            weiXinUtil.updateDept(id, dept.getpId(), deptName);
            logger.info("ID为:{}的部门名称修改为:{}", id, deptName);
        }

    }

    /**
     * 根据mobile查找account
     * @param mobile
     * @return
     */
    @Override
    public Account findByMobile(String mobile) {
        return accountMapper.findByMobile(mobile);
    }

    /**
     * 根据accountId查找deptList
     * @param accountId
     * @return
     */
    @Override
    public List<Dept> findDeptListByAccountId(Integer accountId) {
        return accountMapper.findDeptListByAccountId(accountId);
    }

    /**
     * 根据id查找account
     * @param id
     * @return
     */
    @Override
    public Account findById(Integer id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    /**
     * 管理员修改员工信息
     * @param id
     * @param userName
     * @param mobile
     * @param password
     * @param deptIdArray
     */
    @Override
    @Transactional
    public void editEmployee(Integer id, String userName, String mobile, String password, Integer[] deptIdArray) {
        Account account = accountMapper.selectByPrimaryKey(id);
        if(account == null) {
            throw new ServiceException("员工不存在");
        }
        if(StringUtils.isNotEmpty(password)) {
            String salt = "mysql";
            account.setPassword(DigestUtils.md5Hex(salt + password));
        }
        account.setMobile(mobile);
        account.setUserName(userName);
        account.setUpdateTime(new Date());
        accountMapper.updateByPrimaryKeySelective(account);

        //删除与部门的关联关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);

        //添加关联关系
        insertAccountDept(deptIdArray, account);


    }

    private void insertAccountDept(Integer[] deptIdArray, Account account) {
        for (Integer dId : deptIdArray) {
            AccountDeptKey accountDept = new AccountDeptKey();
            accountDept.setAccountId(account.getId());
            accountDept.setDeptId(dId);
            accountDeptMapper.insert(accountDept);
        }
    }

    /**
     * 员工自己修改密码
     * @param id
     * @param password
     */
    @Override
    public void changePassword(Integer id, String password) {
        Account account = accountMapper.selectByPrimaryKey(id);
        if(StringUtils.isNotEmpty(password)) {
            String mysql = "mysql";
            account.setPassword(DigestUtils.md5Hex(mysql + password));
        }
        account.setUpdateTime(new Date());
        accountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 查找此account以外的部门
     *
     * @param id
     * @return
     */
    @Override
    public List<Dept> findRestDepts(Integer id) {

        List<Dept> all = findAllDept();
        all.remove(0);
        List<Dept> user = findDeptListByAccountId(id);
        int length = all.size() - user.size();
        for(int i = 0; i < all.size(); i++) {
            for(Dept u : user) {
                if(all.get(i).getId() == u.getId()) {
                    all.remove(i);
                    break;
                }
            }
            if(length == all.size()) {
                break;
            }
        }
        return all;
    }
}
