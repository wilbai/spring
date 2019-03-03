package com.wil.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * Created by wil on 2018/11/9.
 * @Async 还有一个参数，通过Bean名称来指定调用的线程池-比如上例中设置的线程池参数不满足业务需求，可以另外定义合适的线程池，
 * 调用时指明使用这个线程池-缺省时springboot会优先使用名称为'taskExecutor'的线程池，如果没有找到，才会使用其他类型
 * 为TaskExecutor或其子类的线程池。
 * 这个类就是自定义线程实现类，用法和其他自定义service一样
 */
@Component
public class MyAsyncService {
    @Async
    public void sayHello(Integer i) {
        System.out.println("hello--- i: " + i + "thread: " + Thread.currentThread().getName());
    }
    @Async("taskExecutor")
    public ListenableFuture<String> hashReturnAsync(String name) {
        String res = name +"-- thread: "+ Thread.currentThread().getName();
        return new AsyncResult<>(res);
    }
    @Async
    public void sendMsgTask(Object... params) {
        System.out.println("because: " + params[0] + "send to: " + params[1] + "content: " + params[2]);
        System.out.println("thread: " + Thread.currentThread().getName());
    }


}
