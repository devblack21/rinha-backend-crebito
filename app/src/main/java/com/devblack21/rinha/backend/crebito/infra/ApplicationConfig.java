package com.devblack21.rinha.backend.crebito.infra;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {
    @Bean
    public JsonMapper jsonMapper() {

        return JsonMapper.builder()
                .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.WRAP_ROOT_VALUE)
                .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .addModule( new JavaTimeModule())
                .build();
    }

}
