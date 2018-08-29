package com.wil;

import com.wil.controller.ProductController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by wil on 2017/12/8.
 */
public class App {
    public static void main(String[] args) throws IOException {
        /*ApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        ProductService productService = (ProductService) context.getBean("productService");
        List<String> productNames = productService.findAllProductNames();
        for(String name : productNames) {
            System.out.println(name);
        }
        productService.save("hello,Dubbo");*/

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ProductController homeController = (ProductController) context.getBean("productController");
        homeController.sayHello();

        System.in.read();

    }

}
