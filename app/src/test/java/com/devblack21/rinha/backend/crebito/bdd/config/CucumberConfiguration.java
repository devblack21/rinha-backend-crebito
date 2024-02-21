package com.devblack21.rinha.backend.crebito.bdd.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberConfiguration.SpringConfiguration.class,
initializers = ConfigDataApplicationContextInitializer.class)
public class CucumberConfiguration {


    @Configuration
    @ComponentScan(basePackages = "com.devblack21.rinha.backend.crebito.bdd")
    static class SpringConfiguration {}

}
