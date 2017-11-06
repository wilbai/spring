package com.wil.controller;

import com.google.common.collect.Maps;
import com.wil.entity.Account;
import com.wil.entity.AccountDept;
import com.wil.entity.Dept;
import com.wil.entity.Json;
import com.wil.service.DeptService;
import com.wil.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/6.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DeptService deptService;



    @GetMapping
    public String show(Integer deptId, Model model) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("deptId", deptId);
        List<Account> accountList = employeeService.findAllByParam(map);
        model.addAttribute("accountList", accountList);
        return "employee/employee";
    }

    @GetMapping("/new/{deptId:\\d+}")
    public String newAccount(@PathVariable Integer deptId, Model model) {
        model.addAttribute("deptId", deptId);
        return "employee/employee-new";
    }

    @PostMapping("/new/{deptId:\\d+}")
    public String newAccount(Account account, @PathVariable Integer deptId, RedirectAttributes redirectAttributes) {
        Dept dept = deptService.findById(deptId);
        System.out.println(dept);
        account.setDept(dept);
        employeeService.save(account);
        AccountDept accountDept = new AccountDept(account.getId(), deptId);
        deptService.saveAccountDept(accountDept);
        redirectAttributes.addFlashAttribute("message", "添加成功");
        return "redirect:/employee";
    }

    @GetMapping("/login")
    public String login() {
        return "employee/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Json login(Account account) {
        Json json = employeeService.login(account);
        return json;
    }



}
