package com.wil.controller;

import com.wil.async.MyAsyncService;
import com.wil.mapper.StudentMapper;
import com.wil.pojo.Product;
import com.wil.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * Created by wil on 2017/12/7.
 */
@Controller
public class HomeController {

    @Autowired
    private ExecutorService executorService;
    @Autowired
    private MyAsyncService myAsyncService;
    @Autowired
    private StudentMapper studentMapper;
    @GetMapping("/")
    public String hello(Model model, HttpSession session) {
        model.addAttribute("product", new Product(661,"Apple 苹果 iPhone X 全面屏手机 深空灰色 全网通 64GB",
                "【原封国行正品 全国联保】送钢化膜+保护壳 全面屏，科技全绽放。 苹果8plus",7999,9999));
        session.setAttribute("accountId",1001);
        model.addAttribute("placeList", Arrays.asList("德国", "美国", "中国"));
        model.addAttribute("inventory",5);
        model.addAttribute("attitude","十分满意");
        return "home";
    }

    @GetMapping("/student/{id:\\d+}")
    @ResponseBody
    public Student student(@PathVariable Integer id) {
        System.out.println("student " + studentMapper.getStudentById(id));

        //springboot封装的executorService
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("========ExecutorService task======= " + Thread.currentThread().getName());
            }
        });
        //自定义线程池 线程
        myAsyncService.sayHello(id);
        myAsyncService.sendMsgTask("您的订单号xx123090的订单已于上个月发货,催款信息", "Mr.Right", "请及时付款");
        return studentMapper.getStudentById(id);
    }

}
