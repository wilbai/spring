package com.wil.proxyprogress;

/**
 * Created by wil on 2017/10/30.
 */
public class ArticleService implements Service {
    @Override
    public void save() {
        System.out.println("articleService save....");
    }

    @Override
    public void count() {
        System.out.println("articleService count...");
    }

}
