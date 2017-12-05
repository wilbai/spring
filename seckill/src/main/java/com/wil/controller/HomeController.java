package com.wil.controller;

import com.wil.controller.result.AjaxResult;
import com.wil.entity.Product;
import com.wil.service.ProductService;
import com.wil.service.exception.ServiceException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by wil on 2017/12/5.
 */
@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "home";
    }

    @GetMapping("/product/new")
    public String newProduct() {
        return "new";
    }

    @PostMapping("/product/new")
    public String newProduct(Product product , MultipartFile image, String sTime, String eTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTime start = formatter.parseDateTime(sTime);
        DateTime end = formatter.parseDateTime(eTime);

        product.setStartTime(start.toDate());
        product.setEndTime(end.toDate());

        if(image.isEmpty()) {
            productService.saveProduct(product, null);
        } else {
            try {
                productService.saveProduct(product, image.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/product/{id:\\d+}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product";
    }

    @GetMapping("/product/seckill/{id:\\d+}")
    @ResponseBody
    public AjaxResult seckill(@PathVariable Integer id) {
        try{
            productService.seckill(id);
            return AjaxResult.success();
        } catch (ServiceException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }





}
