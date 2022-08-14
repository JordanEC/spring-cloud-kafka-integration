package com.jordanec.store.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.jordanec.store.consumer.entity")
@EnableJpaRepositories(basePackages = "com.jordanec.store.consumer.repository")
@EnableScheduling
@EnableRetry
public class ConsumerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
