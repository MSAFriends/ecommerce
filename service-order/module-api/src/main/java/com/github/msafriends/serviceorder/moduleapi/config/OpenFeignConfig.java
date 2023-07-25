package com.github.msafriends.serviceorder.moduleapi.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.github.msafriends.serviceorder.moduleapi.client")
public class OpenFeignConfig {
}
