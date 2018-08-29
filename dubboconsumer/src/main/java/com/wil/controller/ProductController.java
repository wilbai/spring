package com.wil.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wil.service.ProductService;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by wil on 2017/12/11.
 */
@Controller
public class ProductController {

    @Reference
    private ProductService productService;

    public void sayHello() {
        List<String> stringList =productService.findAllProductNames();
        for(String str : stringList) {
            System.out.println(str);
        }
    }


}
