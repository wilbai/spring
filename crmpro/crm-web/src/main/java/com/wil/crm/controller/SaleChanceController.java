package com.wil.crm.controller;

import com.github.pagehelper.PageInfo;
import com.wil.crm.controller.exception.ForbiddenException;
import com.wil.crm.controller.exception.NotFoundException;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.entity.SaleChanceRecord;
import com.wil.crm.service.CustomerService;
import com.wil.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wil on 2017/11/13.
 */
@Controller
@RequestMapping("/sales")
public class SaleChanceController extends BaseController{

    @Autowired
    private SaleChanceService saleChanceService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/my")
    public String mySaleChances(@RequestParam(name = "p", required = false, defaultValue = "1") Integer pageNo,
                                HttpSession session, Model model) {
        Account account = getCurrentAccount(session);
        PageInfo<SaleChance> pageInfo = saleChanceService.pageMySaleChances(account, pageNo);
        model.addAttribute("page", pageInfo);
        return "sales/my";
    }

    @GetMapping("/my/{id:\\d+}")
    public String myChanceDetail(@PathVariable Integer id,
                                 HttpSession session, Model model) {
        SaleChance saleChance = checkRowPower(id, session);
        List<SaleChanceRecord> recordList = saleChanceService.findAllRecordListBySaleId(id);

        model.addAttribute("recordList", recordList);
        model.addAttribute("saleChance", saleChance);
        model.addAttribute("progressList", saleChanceService.findAllProgress());

        return "sales/show";
    }

    /**
     * 新增销售机会
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/my/new")
    public String newChance(Model model, HttpSession session) {
        Account account = getCurrentAccount(session);
        List<Customer> customerList = customerService.findCustomerListByAccountId(account);
        List<String> progressList = saleChanceService.findAllProgress();

        model.addAttribute("customerList", customerList);
        model.addAttribute("progressList", progressList);
        return "sales/new";
    }

    @PostMapping("/my/new")
    public String newChance(SaleChance saleChance, HttpSession session,
                            RedirectAttributes redirectAttributes) {
        saleChanceService.saveSaleChance(saleChance);
        redirectAttributes.addFlashAttribute("message", "新增销售机会成功");
        return "redirect:/sales/my/"+saleChance.getId();
    }

    /**
     * 删除销售机会
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deleteChance(@PathVariable Integer id, HttpSession session,
                               RedirectAttributes redirectAttributes) {
        checkRowPower(id, session);
        saleChanceService.deleteSaleChanceById(id);
        return "redirect:/sales/my";

    }

    /**
     * 添加新的跟进记录
     * @param record
     * @param session
     * @return
     */
    @PostMapping("/my/new/record")
    public String newChanceRecord(SaleChanceRecord record, HttpSession session) {
        checkRowPower(record.getSaleId(), session);
        saleChanceService.saveSaleChanceRecord(record);
        return "redirect:/sales/my/"+record.getSaleId();
    }

    /**
     * 更新销售机会进度
     * @param id
     * @param progress
     * @param session
     * @return
     */
    @PostMapping("/my/{id:\\d+}/progress/update")
    public String updateSaleChanceState(@PathVariable Integer id, String progress,
                                        HttpSession session) {
        checkRowPower(id, session);
        saleChanceService.updateChanceState(id, progress);
        return "redirect:/sales/my/"+id;

    }



    private SaleChance checkRowPower(@PathVariable Integer id, HttpSession session) {
        SaleChance saleChance = saleChanceService.findSaleChanceWithCustomerById(id);
        Account account = getCurrentAccount(session);
        if(saleChance == null) {
            throw new NotFoundException("销售机会不存在");
        }
        if(!saleChance.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("权限不足");
        }
        return saleChance;
    }


}
