package com.wil.crm.controller;

import com.github.pagehelper.PageInfo;
import com.wil.crm.auth.ShiroUtil;
import com.wil.crm.controller.exception.ForbiddenException;
import com.wil.crm.controller.exception.NotFoundException;
import com.wil.crm.entity.Account;
import com.wil.crm.entity.Customer;
import com.wil.crm.entity.SaleChance;
import com.wil.crm.service.AccountService;
import com.wil.crm.service.CustomerService;
import com.wil.crm.service.SaleChanceService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 客户管理控制器
 * Created by wil on 2017/11/10.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 展示我的客户列表
     * @param pageNo
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String myCustomer(@RequestParam(name = "p", required = false, defaultValue = "1") Integer pageNo,
                             HttpSession session, Model model) {
        Account account = getCurrentAccount(session);
        PageInfo<Customer> pageInfo = customerService.pageForMyCustomer(pageNo, account);
        model.addAttribute("page", pageInfo);
        return "customer/my";
    }

    /**
     * 新增客户
     * @param model
     * @return
     */
    @GetMapping("/my/new")
    public String newCustomer(Model model) {
        model.addAttribute("sources", customerService.findAllCusSource());
        model.addAttribute("trades", customerService.findAllCusTrade());
        return "customer/new";
    }

    @PostMapping("my/new")
    public String newCustomer(Customer customer, RedirectAttributes redirectAttributes) {
        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("message","新增客户成功");
        return "redirect:/customer/my/" + customer.getId();
    }

    /**
     * 客户详情展示
     * @param id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String showCustomer(@PathVariable Integer id,
                               HttpSession session, Model model) {
        Customer customer = checkAccountPower(id, session);
        List<SaleChance> saleChanceList = saleChanceService.findAllChancesByCustomerId(id);

        model.addAttribute("saleChanceList", saleChanceList);
        model.addAttribute("customer", customer);
        model.addAttribute("accountList", accountService.findAllAccount());
        return "customer/show";
    }

    /**
     * 展示公海客户详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/public/{id:\\d+}")
    public String showPubCus(@PathVariable Integer id, Model model) {
        Customer customer = customerService.findCusById(id);
        if(customer == null) {
            throw new NotFoundException("客户:"+id+"不存在或已被删除");
        }
        List<SaleChance> saleChanceList = saleChanceService.findAllChancesByCustomerId(id);

        model.addAttribute("saleChanceList", saleChanceList);
        model.addAttribute("customer", customer);

        return "customer/show";
    }

    /**
     * 从公海接收客户
     * @param id
     * @return
     */
    @GetMapping("/public/{id:\\d+}/get")
    public String getCustomerFromPublic(@PathVariable Integer id) {
        Customer customer = customerService.findCusById(id);
        if(customer == null || customer.getAccountId() != 0) {
            throw new NotFoundException("客户:"+id+"不存在或已被删除");
        }
        Account account = ShiroUtil.getCurrentAccount();
        customerService.getCusFromPub(customer, account);
        return "redirect:/customer/my";
    }

    /**
     * 修改客户资料
     * @param id
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String editCustomer(@PathVariable Integer id,
                               HttpSession session, Model model) {
       Customer customer = checkAccountPower(id, session);
       model.addAttribute("customer", customer);
       model.addAttribute("sources", customerService.findAllCusSource());
       model.addAttribute("trades", customerService.findAllCusTrade());
       return "customer/edit";
    }

    @PostMapping("/my/{id:\\d+}/edit")
    public String editCustomer(@PathVariable Integer id, Customer customer,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        checkAccountPower(id, session);
        customerService.editCustomer(customer);
        redirectAttributes.addFlashAttribute("message","客户资料修改成功");
        return "redirect:/customer/my/"+ customer.getId();
    }

    /**
     * 转交客户
     * @param customerId
     * @param accountId
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{customerId:\\d+}/trans/{accountId:\\d+}")
    public String transCustomer(@PathVariable Integer customerId,
                                @PathVariable Integer accountId,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = checkAccountPower(customerId, session);
        customerService.transCustomer(customer, accountId);
        redirectAttributes.addFlashAttribute("message", "转交客户成功");
        return "redirect:/customer/my";
    }

    /**
     * 将客户放入公海
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/public")
    public String publicCustomer(@PathVariable Integer id, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Customer customer = checkAccountPower(id, session);
        customerService.publicCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "将客户放入公海成功");
        return "redirect:/customer/my";
    }


    /**
     * 删除客户
     * @param id
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deleteCustomer(@PathVariable Integer id, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Customer customer = checkAccountPower(id, session);
        customerService.deleteCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "删除客户成功");
        return "redirect:/customer/my";
    }

    /**
     * 将客户信息导出为csv文件
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/my/export.csv")
    public void exportToCsvFile(HttpServletResponse response, HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);

        response.setContentType("text/csv;charset=GBK");
        String fileName = new String("我的客户.csv".getBytes("UTF-8"), "ISO8859-1");
        response.addHeader("Content-Disposition","attachment;fileName=\""+fileName+"\"");
        OutputStream outputStream = response.getOutputStream();
        exportDataToCsvFile(account, outputStream);
    }

    public void exportDataToCsvFile(Account account, OutputStream outputStream) throws IOException {
        List<Customer> customerList = customerService.findCustomerListByAccountId(account);

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
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/my/export.xls")
    public void exportToXlsFile(HttpServletResponse response, HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);

        response.setContentType("application/vnd.ms-excel");
        String fileName = new String("我的客户.xls".getBytes("UTF-8"), "ISO8859-1");
        response.addHeader("Content-Disposition","attachment;fileName=\""+fileName+"\"");
        OutputStream outputStream = response.getOutputStream();
        exportDataToXlsFile(account, outputStream);
    }

    public void exportDataToXlsFile(Account account, OutputStream outputStream) throws IOException {
        List<Customer> customerList = customerService.findCustomerListByAccountId(account);
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
     * 公海客户
     * @param pageNo
     * @param model
     * @return
     */
    @GetMapping("/public")
    public String publicCustomer(@RequestParam(name = "p", required = false, defaultValue = "1") Integer pageNo,
                                 Model model) {
        PageInfo<Customer> pageInfo = customerService.pageForPublicCustomer(pageNo);
        model.addAttribute("page", pageInfo);
        return "customer/public";
    }

    /**
     * 验证该customer是否存在，是否属于当前user
     * @param id customerID
     * @param session
     * @return
     */
    private Customer checkAccountPower(@PathVariable Integer id,
                                       HttpSession session) {

        Customer customer = customerService.findCusById(id);
        //非法id-customer不存在
        if(customer == null) {
            throw new NotFoundException("客户"+id+"不存在");
        }
        //非法id-无权限
        if(!customer.getAccountId().equals(getCurrentAccount(session).getId())) {
            throw new ForbiddenException("您没有查看该客户"+id+"的权限");
        }
        return customer;
    }



}
