package com.example.demo.service.product.config;

import com.example.demo.common.exception.ApiErrorMessageFactory;
import com.example.demo.common.exception.GlobalExceptionHandler;
import com.example.demo.common.exception.GlobalExceptionHandlerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class OrderExceptionHandlerConfig implements GlobalExceptionHandlerConfigurer {

    private static final Pattern CONSTRAINT_PATTERN = Pattern.compile(".*constraint \\[\"(.+?):.*");

    @Override
    public void putApiErrorMappers(GlobalExceptionHandler.ApiErrorMappers mappers, ApiErrorMessageFactory apiError) {
        mappers.put(DataIntegrityViolationException.class, ex -> {
            String message = ex.getMessage();
            if (message != null) {
                Matcher matcher = CONSTRAINT_PATTERN.matcher(message);
                if (matcher.find()) {
                    String code = matcher.group(1);
                    if (code != null) {
                        return apiError.notFound(code);
                    }
                }
            }
            return apiError.internalServerError("SQL error doesn't contain constraint data");
        });
    }
}
