package com.wil.crm.service;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Dept;
import com.wil.crm.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/7.
 */
public interface AccountService {

    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    Account login(String mobile, String password);

    /**
     * 添加新部门
     * @param deptName
     */
    void saveDept (String deptName) throws ServiceException;

    List<Dept> findAllDept();

    /**
     * 根据参数查找分页后的list
     * @param map
     * @return
     */
    List<Account> pageByParam(Map<String, Object> map);

    /**
     * 根据deptId查找account的总数
     * @param deptId
     * @return
     */
    Long countByDeptId(Integer deptId);

    /**
     * 添加新员工
     * @param userName
     * @param mobile
     * @param password
     * @param deptIdArray 部门可以多选
     */
    void saveEmployee(String userName, String mobile, String password, Integer[] deptIdArray);

    /**
     * 根据id删除员工
     * @param id
     */
    void delEmployeeById(Integer id);

    /**
     * 查找所有user
     * @return
     */
    List<Account> findAllAccount();

    void delDeptById(Integer id);

    void editDept(Integer id, String deptName);

}
