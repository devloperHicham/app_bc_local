package com.schedulerates.comparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.schedulerates.comparison.client")
public class ComparisonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparisonServiceApplication.class, args);
	}

}