package com.example.demo.common.openapi.customizer;

import com.example.demo.common.openapi.annotation.ApiSecuredOperation;
import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

public class CustomOperationCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiSecuredOperation annotation = handlerMethod.getMethodAnnotation(ApiSecuredOperation.class);
        if (annotation != null) {


        }
        return null;
    }
}
