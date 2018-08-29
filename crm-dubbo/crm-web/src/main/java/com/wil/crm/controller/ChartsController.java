package com.wil.crm.controller;

import com.wil.crm.entity.Account;
import com.wil.crm.service.CustomerService;
import com.wil.crm.service.SaleChanceService;
import com.wil.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wil on 2017/11/18.
 */
@Controller
@RequestMapping("/charts")
public class ChartsController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleChanceService saleChanceService;

    @GetMapping
    public String show() {
        return "charts/chart";
    }

    @GetMapping("/sale")
    public String saleChart() {
        return "charts/saleChart";
    }

    @GetMapping("/level")
    @ResponseBody
    public AjaxResult customerLevel(HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Map<String, Object>> levelCount = customerService.countCustomerByLevel(account);
        return new AjaxResult().successWithData(levelCount);
    }

    @GetMapping("/process")
    @ResponseBody
    public AjaxResult saleChanceFunnel(HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Map<String, Object>> processCount = saleChanceService.countChanceByProcess(account);
        return new AjaxResult().successWithData(processCount);
    }

    @GetMapping("/newCus")
    @ResponseBody
    public AjaxResult newCusMonthlyLine(HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Map<String, Object>> customerCount = customerService.countCustomerByCreateTime(account);
        return new AjaxResult().successWithData(customerCount);
    }



}
