package com.github.msafriends.serviceorder.moduleapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.github.msafriends.serviceorder.modulecore.repository")
public class JpaRepositoryConfig {
}
