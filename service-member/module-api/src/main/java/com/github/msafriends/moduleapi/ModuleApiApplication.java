package com.github.msafriends.moduleapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.github.msafriends", "com.github.msafriends.modulecore"})
@EntityScan("com.github.msafriends.modulecore")
@EnableJpaRepositories("com.github.msafriends.modulecore")
public class ModuleApiApplication {
    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ModuleApiApplication.class, args);
    }

/*    @Override
    public void run(String... args) throws Exception {
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (String bean: beans) {
            log.info("bean = {}", bean);
        }
    }*/

}
