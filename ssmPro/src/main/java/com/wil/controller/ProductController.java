package com.wil.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.wil.entity.Kaola;
import com.wil.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Created by wil on 2017/11/5.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private KaolaService kaolaService;

    @GetMapping
    public String list(@RequestParam(name = "p", required = false,
            defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false, defaultValue = "") String productName,
                       @RequestParam(required = false, defaultValue = "") String place,
                       @RequestParam(required = false, defaultValue = "") Integer typeId,
                       @RequestParam(required = false, defaultValue = "") Integer price,
                       Model model) {

        Map<String, Object> queryParam = Maps.newHashMap();
        queryParam.put("productName", productName);
        queryParam.put("place", place);
        queryParam.put("typeId", typeId);
        queryParam.put("price", price);

        PageInfo pageInfo = kaolaService.findByPageNo(queryParam, pageNo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("typeList", kaolaService.findAllType());
        model.addAttribute("placeList", kaolaService.findAllPlace());

        return "product/list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("typeList", kaolaService.findAllType());
        return "product/new";
    }

    @PostMapping("/new")
    public String newProduct(Kaola kaola, RedirectAttributes redirectAttributes) {
        kaolaService.save(kaola);
        redirectAttributes.addFlashAttribute("message", "添加成功");
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}")
    public String show(@PathVariable Integer id, Model model) {
        Kaola kaola = kaolaService.findById(id);
        model.addAttribute("product", kaola);
        return "product/show";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("product", kaolaService.findById(id));
        model.addAttribute("typeList", kaolaService.findAllType());
        return "product/edit";
    }


    @PostMapping("/{id:\\d+/edit}")
    public String edit(Kaola kaola, RedirectAttributes redirectAttributes) {
        kaolaService.edit(kaola);
        redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/product/" + kaola.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        kaolaService.delete(id);
        redirectAttributes.addFlashAttribute("message", "删除成功");
        return "redirect:/product";
    }




}
