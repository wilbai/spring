package com.wil;

import com.wil.async.MyAsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	private ExecutorService executorService;
	@Autowired
	private MyAsyncService myAsyncService;

	@Test
	public void contextLoads() {
		System.out.println("hello springboot");
	}

	public void threadTest() {
		System.out.println("");
	}

	@Test
	public void helloAsyncTest() {

		for(int i = 0; i < 10; i++) {
			System.out.println("helloAsync begin--- " + Thread.currentThread().getName());
			myAsyncService.sayHello(i);
			System.out.println("helloAsync end--- " + Thread.currentThread().getName());
		}
	}

	@Test
	public void hashReturnAsyncTest() throws InterruptedException, ExecutionException, TimeoutException {
		for(int i = 0; i < 6; i++) {
			System.out.println("hashReturnAsyncTest begin--- " + Thread.currentThread().getName());
			String res = myAsyncService.hashReturnAsync("seber" + i).get(1, TimeUnit.SECONDS);
			System.out.println("res: " + res);
			System.out.println("hashReturnAsyncTest end--- " + Thread.currentThread().getName());
		}
	}

}
