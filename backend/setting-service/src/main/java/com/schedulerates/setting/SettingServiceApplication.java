package com.schedulerates.setting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.schedulerates.setting.storage.FileStorageProperties;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.schedulerates.setting.client")
@EnableConfigurationProperties(FileStorageProperties.class) // Add this annotation to enable configuration properties
public class SettingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettingServiceApplication.class, args);
	}

}