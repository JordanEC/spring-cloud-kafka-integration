package com.jordanec.store.consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application-test.yml")
@Profile("test")
public class H2JpaConfig
{
}
