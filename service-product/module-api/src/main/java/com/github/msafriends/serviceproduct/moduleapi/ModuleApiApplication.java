package com.github.msafriends.serviceproduct.moduleapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.github.msafriends"})
@EnableJpaRepositories("com.github.msafriends.serviceproduct.modulecore.repository")
@EntityScan(basePackages = "com.github.msafriends.serviceproduct.modulecore.domain")
public class ModuleApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ModuleApiApplication.class, args);
	}
}
