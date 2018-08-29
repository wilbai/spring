package com.wil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BootDubboProviderApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BootDubboProviderApplication.class, args);
		System.out.println("provider start....");
	}
}
