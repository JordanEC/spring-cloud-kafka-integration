package com.jordanec.store.producer.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/integration-test/resources/features/",
        glue = "classpath:com.jordanec.store.producer.cucumber",
        plugin = {"pretty", "json:target/cucumber-report.json"})
public class RunCucumberTest {
}
