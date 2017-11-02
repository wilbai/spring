package com.wil.controller;

import com.wil.entity.User;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by wil on 2017/11/2.
 */
@Controller
public class HelloController {

    //@RequestMapping(value = "/hello", method = {RequestMethod.GET,RequestMethod.POST})
    @GetMapping("/hello")
    @PostMapping("/hello")
    public String sayHello() {
        System.out.println("hello, SpringMVC!");
        return "hello";
    }

    @GetMapping("/send")
    public ModelAndView send() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("send");
        modelAndView.addObject("message", "使用ModelAndView");
        return modelAndView;
    }
    /*public String send(Model model) {
        model.addAttribute("message", "hello, spring...");
        return "send";
    }*/

   /* @GetMapping("/blog")
    public String blog(@RequestParam("id") Integer userId, String name, Model model) {
        if(name != null & name != "") {
            try {
                name = new String(name.getBytes("ISO8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("id->" + userId + "name->" + name );
        model.addAttribute("id", userId);
        model.addAttribute("name", name);
        return "hello";

    }*/

    @GetMapping("/blog")
    public String blog(User user) {
        System.out.println("id->" + user.getId() + "name->" + user.getName());
        return "hello";
    }

    //会自动将能够匹配的值传回请求转发的页面
    @GetMapping("/name/{name}/movie/{id:\\d+}")
    public String movie(@PathVariable String name,
                        @PathVariable Integer id) {
        System.out.println("movieName>>>>" + name +"movieId>>>>" + id);
        return "hello";
    }

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(User user, String email,
                       MultipartFile photo,
                       RedirectAttributes redirectAttributes) {

        System.out.println("password>>> " + user.getPassword() + "userName>>> " + user.getName()
        + "email>>> " + email );
        if(!photo.isEmpty()){
            System.out.println("文件大小:" + photo.getSize());
            System.out.println("文件名称:" + photo.getOriginalFilename());
            System.out.println("MIMEType:" + photo.getContentType());
            try {
                IOUtils.copy(photo.getInputStream(), new FileOutputStream("d:/logs/"+ photo.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("message", "资料已提交");
        return "redirect:save";
    }







}
