package com.br.order.infrastructure.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    @Bean("defaultObjectMapper")
    @Primary
    public ObjectMapper defaultObjectMapper() {
        return buildObjectMapper();
    }

    private ObjectMapper buildObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToDisable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
                .featuresToDisable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .modules(new JavaTimeModule())
                .build();
    }
}
