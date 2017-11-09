package com.wil.crm.controller;

import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.exception.ServiceException;
import com.wil.crm.service.CustomerService;
import com.wil.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wil on 2017/11/9.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/my")
    public String list(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("currentAccount");
        List<Customer> customerList = customerService.findAllCustomer(account.getId());
        model.addAttribute("customerList", customerList);
        return "customer/myList";
    }

    @GetMapping("/new")
    public String newCustomer() {
        return "customer/new";
    }

    @PostMapping("/new")
    public String newCustomer(Customer customer, HttpSession session, RedirectAttributes redirectAttributes) {

        Account currentAccount = (Account) session.getAttribute("currentAccount");
        customer.setAccountId(currentAccount.getId());
        try {
            customerService.saveCustomer(customer);
            redirectAttributes.addFlashAttribute("suc_message", "添加客户成功");
            return "redirect:/customer/my";

        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("err_message", ex.getMessage());
            return "redirect:/customer/my";
        }
    }

    @GetMapping("/{id:\\d+}")
     public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findCustomerById(id));
        return "customer/detail";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.findCustomerById(id));
        return "customer/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customer.setId(id);
            customerService.editCustomer(customer);
            return "redirect:/customer/"+id;
        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("err_message", ex.getMessage());
            return "redirect:/customer/my";
        }
    }

    @GetMapping("/publicSea.json")
    @ResponseBody
    public AjaxResult publicSea(Integer id) {
        customerService.drawCusToSea(id);
        return new AjaxResult().success();
    }

    @GetMapping("/delCus.json")
    @ResponseBody
    public AjaxResult delete(Integer id) {
        customerService.delCustomer(id);
        return new AjaxResult().success();
    }



}
