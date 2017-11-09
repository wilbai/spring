package com.wil.crm.service.impl;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.AccountDeptKey;
import com.wil.crm.entity.Dept;
import com.wil.crm.example.AccountDeptExample;
import com.wil.crm.example.AccountExample;
import com.wil.crm.example.DeptExample;
import com.wil.crm.exception.AuthenticationException;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.mapper.AccountDeptMapper;
import com.wil.crm.mapper.AccountMapper;
import com.wil.crm.mapper.DeptMapper;
import com.wil.crm.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveDept(String deptName) throws ServiceException {
        Dept dept = deptMapper.findByName(deptName);
        if (dept != null) {
            throw new ServiceException("部门已存在");
        } else {
            dept = new Dept();
            dept.setDeptName(deptName);
            dept.setpId(COMPANY_ID);
            deptMapper.insertSelective(dept);
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

        if(deptId == null && COMPANY_ID.equals(deptId)) {
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
        account.setPassword(DigestUtils.md5Hex(password + salt));
        accountMapper.insertSelective(account);
        //添加关联关系
        for (Integer dId : deptIdArray) {
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setAccountId(account.getId());
            accountDeptKey.setDeptId(dId);
            accountDeptMapper.insert(accountDeptKey);
        }
        logger.info("添加新员工：{}, 时间：{}",userName, new Date());
    }

    /**
     * 根据id删除员工
     * @param id
     */
    @Override
    @Transactional
    public void delEmployeeById(Integer id) throws ServiceException {
        //TODO 判断其他的关联关系
        //删除与部门的关联关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);
        accountDeptMapper.deleteByExample(accountDeptExample);
        //删除员工
        accountMapper.deleteByPrimaryKey(id);
        logger.info("删除ID为:{}的员工",id);
    }
}
