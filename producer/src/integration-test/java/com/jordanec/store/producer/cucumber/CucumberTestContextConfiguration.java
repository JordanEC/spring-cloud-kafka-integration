package com.jordanec.store.producer.cucumber;

import com.jordanec.store.producer.H2JpaConfig;
import com.jordanec.store.producer.ProducerApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ProducerApp.class,
        H2JpaConfig.class })
public class CucumberTestContextConfiguration {
}
