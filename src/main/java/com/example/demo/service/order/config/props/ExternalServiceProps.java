package com.example.demo.service.order.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Getter
@Setter
@Validated
@ConfigurationProperties("external-service")
public class ExternalServiceProps {
    @Valid
    private final ProductServiceProps productService = new ProductServiceProps();

    @Getter
    @Setter
    public static class ProductServiceProps {
        @NotNull
        private URL url;
    }
}
