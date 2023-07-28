package com.github.msafriends.serviceorder.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.github.msafriends.modulecommon", "com.github.msafriends.serviceorder.moduleapi"})
@EntityScan(basePackages = "com.github.msafriends.serviceorder.modulecore.domain")
public class ModuleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}

}
