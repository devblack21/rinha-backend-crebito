package com.devblack21.rinha.backend.crebito.infra;

import io.github.devblack21.logging.LogBit;
import io.github.devblack21.logging.configuration.LogBitConfiguration;
import io.github.devblack21.logging.enginer.DefaultEngineLogBit;
import io.github.devblack21.logging.enginer.EngineLogBit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public LogBitConfiguration logConfiguration() {
        return new LogBitConfiguration("Rinha-Backend",
                "DevBlack21", "Api");
    }

    @Bean
    public EngineLogBit abstractEngineBitLogger(final LogBitConfiguration configuration) {
        final EngineLogBit abstractEngineBitLogger = new DefaultEngineLogBit(configuration);
        LogBit.configure(abstractEngineBitLogger);
        return abstractEngineBitLogger;
    }

}
