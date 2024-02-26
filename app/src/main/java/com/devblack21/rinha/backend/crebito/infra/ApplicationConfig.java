package com.devblack21.rinha.backend.crebito.infra;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.github.devblack21.logging.LogBit;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ApplicationConfig {
    @Bean
    public JsonMapper jsonMapper() {
        return new JsonMapper();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
      LogBit.info("APP_START", "Aplicação em execução...");
    }

}
