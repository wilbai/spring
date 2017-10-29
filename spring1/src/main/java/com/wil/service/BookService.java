package com.wil.service;

/**
 * Created by wil on 2017/10/29.
 */
public class BookService {

    public void hello() {
        System.out.println("hello bookService....");
    }

    public int count() {
        if(10 == 1) {
            throw new RuntimeException("什么鬼.....");
        }
        System.out.println("bookService count...");
        return 100;
    }
}
