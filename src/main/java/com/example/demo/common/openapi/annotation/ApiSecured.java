package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

import static com.example.demo.common.openapi.OpenApiConfig.SECURITY_SCHEMA;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@SecurityRequirement(name = SECURITY_SCHEMA)
public @interface ApiSecured {
}
