package com.wil.crm.controller;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Dept;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.service.AccountService;
import com.wil.web.result.AjaxResult;
import com.wil.web.result.DataTableResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     * 删除部门
     * @param id
     * @return
     */
    @GetMapping("/dept/{id:\\d+}/delDept")
    @ResponseBody
    public AjaxResult delDept(@PathVariable Integer id) {
        try {
            accountService.delDeptById(id);
            return new AjaxResult().success();
        } catch (ServiceException ex) {
            return new AjaxResult().error(ex.getMessage());
        }
    }

    /**
     * 修改部门名称
     * @param id
     * @param deptName
     * @return
     */
    @GetMapping("/dept/{id:\\d+}/editDept")
    @ResponseBody
    public AjaxResult editDept(@PathVariable Integer id, String deptName) {
        try {
            accountService.editDept(id, deptName);
            return new AjaxResult().success();
        } catch (ServiceException ex) {
            return new AjaxResult().error(ex.getMessage());
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

    /**
     * 员工修改密码
     * @return
     */
    @GetMapping("/{id:\\d+}/changePass")
    public String changePassword(@PathVariable Integer id) {
        return "employee/changePass";
    }

    @PostMapping("/{id:\\d+}/changePass")
    public String changePassword(@PathVariable Integer id, String password) {
        accountService.changePassword(id, password);
        return "redirect:/";
    }

    /**
     * 管理原修改员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id:\\d+}/editEmployee")
    public String editEmployee(@PathVariable Integer id, Model model) {
        Account account = accountService.findById(id);
        List<Dept> deptList = accountService.findDeptListByAccountId(id);
        List<Dept> restDepts = accountService.findRestDepts(id);
        model.addAttribute("account", account);
        model.addAttribute("deptList", deptList);
        model.addAttribute("restDepts", restDepts);
        return "employee/edit";
    }


    @PostMapping("/{id:\\d+}/editEmployee")
    public String editEmployee(@PathVariable Integer id, String userName, String mobile,
                               String password, Integer[] deptIdArray) {
        accountService.editEmployee(id, userName, mobile, password, deptIdArray);
        List<Dept> deptList = accountService.findDeptListByAccountId(id);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("deptList", deptList);
        return "redirect:/employee";
    }







}
