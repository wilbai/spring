package com.wil.controller;

import com.wil.pojo.Product;
import com.wil.pojo.ProductType;
import com.wil.service.ProductService;
import com.wil.util.Page;
import com.wil.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wil on 2017/11/29.
 */
@Controller
@RequestMapping("/product")
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String list(Model model, HttpServletRequest request,
                       @RequestParam(name = "p", required = false, defaultValue = "1") Integer pageNo) {
        List<RequestQuery> queryList = RequestQuery.buildRequestQuery(request);
        Page<Product> productList = productService.findByRequestQuery(queryList, pageNo);
        model.addAttribute("page", productList);
        return "list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        List<ProductType> typeList = productService.findAllType();
        model.addAttribute("typeList", typeList);
        return "new";
    }

    @PostMapping("/new")
    public String newProduct(Product product, Integer typeId) {
        ProductType type = productService.findTypeById(typeId);
        product.setProductType(type);
        productService.saveProduct(product);
        return "redirect:/product/"+product.getId();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("typeList", productService.findAllType());
        return "edit";
    }
    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id, Product product,Integer typeId) {
        ProductType type = productService.findTypeById(typeId);
        product.setProductType(type);
        productService.editProduct(product);
        return "redirect:/product/"+id;
    }

    @GetMapping("/{id:\\d+}")
    public String show(@PathVariable Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "show";
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/product";
    }


}
