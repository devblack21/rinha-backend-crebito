package com.devblack21.rinha.backend.crebito.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/reports/report.html",
                "json:target/reports/cucumber.json",
                "timeline:target/reports/timeline"
        },
        tags = "@Transaction",
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        stepNotifications = true,
        features = "classpath:features",
        glue = {
                "com.devblack21.rinha.backend.crebito.bdd.steps",
                "com.devblack21.rinha.backend.crebito.bdd.config"
        }
)
public class JUnitRunner {

    @BeforeClass
    public static void start() {}

}
