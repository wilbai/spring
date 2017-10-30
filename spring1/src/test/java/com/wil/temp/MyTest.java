package com.wil.temp;

import com.wil.proxyprogress.ArticleService;
import com.wil.proxyprogress.Service;
import com.wil.proxyprogress.cglib.MyCglibProxy;
import com.wil.proxyprogress.jdkpro.MyJdkProxy;
import com.wil.proxyprogress.jdkpro.NodeService;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by wil on 2017/10/30.
 */
public class MyTest {

    @Test
    public void jdkProxy() {

        //目标对象
        Object target;
        //代理对象
        //目标对象的接口指向Proxy创建的代理实例

        ArticleService articleService = new ArticleService();
        target = articleService;
        MyJdkProxy myJdkProxy = new MyJdkProxy(target);


        Service service = (Service) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                myJdkProxy);

        service.save();

    }

    @Test
    public void cglibTest() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ArticleService.class);
        enhancer.setCallback(new MyCglibProxy());
        //NodeService nodeService = (NodeService) enhancer.create();
        Service articleService = (ArticleService) enhancer.create();
        System.out.println(articleService.getClass().getName());
        articleService.save();
    }


}
