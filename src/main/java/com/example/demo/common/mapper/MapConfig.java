package com.example.demo.common.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provide auto-configuration of Mapper beans in other modules
 */
@Configuration
public class MapConfig {
    @Bean
    public NotNullChecker notNullChecker() {
        return new NotNullChecker();
    }
}
