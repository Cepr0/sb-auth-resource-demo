package com.example.demo.service.order.config;

import com.example.demo.service.order.config.props.ExternalServiceProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ExternalServiceProps.class)
public class AppConfig {
    private final ExternalServiceProps extServiceProps;

    public AppConfig(ExternalServiceProps extServiceProps) {
        this.extServiceProps = extServiceProps;
    }

    @Bean
    public RestTemplate productApi(RestTemplateBuilder templateBuilder) {
        return templateBuilder
                .rootUri(extServiceProps.getProductService().getUrl().toString())
                .build();
    }
}
