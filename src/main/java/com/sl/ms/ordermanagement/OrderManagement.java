package com.sl.ms.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan(basePackages = "com.sl.ms.*")
public class OrderManagement {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagement.class);
	}
}
