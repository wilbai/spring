package com.wil.crm.controller;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Dept;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.service.AccountService;
import com.wil.web.result.AjaxResult;
import com.wil.web.result.DataTableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/8.
 */
@Controller
@RequestMapping("/employee")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * get跳转list.jsp
     * @return
     */
    @GetMapping
    public String list() {
        return "employee/list";
    }

    /**
     * 获取所有的部门
     * @return
     */
    @GetMapping("/dept.json")
    @ResponseBody
    public List<Dept> findAllDept() {
        return accountService.findAllDept();
    }

    /**
     * 添加部门
     * @param deptName
     * @return
     */
    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult newDept(String deptName) {
        try {
            accountService.saveDept(deptName);
            return AjaxResult.success();
        } catch (ServiceException ex) {
            ex.printStackTrace();
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * DataTables插件请求时返回DataTableResult对象，含有总页数
     * 加载员工列表，可能存在的条件：deptId,accountName(模糊查询),分页
     * @param draw 会话：表示第几次请求
     * @param start
     * @param length
     * @param deptId
     * @param request
     * @return
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTableResult<Account> loadEmployeeList(
            Integer draw, Integer start, Integer length,
            Integer deptId, HttpServletRequest request) {

        String keyword = request.getParameter("search[value]");

        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("length", length);
        map.put("accountName", keyword);
        map.put("deptId", deptId);

        List<Account> accountList = accountService.pageByParam(map);
        Long total = accountService.countByDeptId(deptId);
        return new DataTableResult<>(draw, total.intValue(), accountList);
    }

    /**
     * 添加新员工
     * @param userName
     * @param mobile
     * @param password
     * @param deptIdArray
     * @return
     */
    @PostMapping("/new")
    @ResponseBody
    public AjaxResult newEmployee(String userName, String mobile,
                                  String password, Integer[] deptIdArray) {
        try{
            System.out.println(deptIdArray);
            accountService.saveEmployee(userName, mobile, password, deptIdArray);
            return new AjaxResult().success();
        } catch (ServiceException ex) {
            return new AjaxResult().error(ex.getMessage());
        }
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}/delEmployee")
    @ResponseBody
    public AjaxResult delEmployee(@PathVariable Integer id) {
        try {
            accountService.delEmployeeById(id);
            return new AjaxResult().success();
        } catch (ServiceException ex) {
            return new AjaxResult().error(ex.getMessage());
        }
    }



}
