package com.example.demo.common.openapi.customizer;

import com.example.demo.common.openapi.annotation.ApiSecured;
import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

public class CustomOperationCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiSecured annotation = handlerMethod.getMethodAnnotation(ApiSecured.class);
        if (annotation != null) {


        }
        return null;
    }
}
