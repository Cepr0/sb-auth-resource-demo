package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

import static com.example.demo.common.openapi.OpenApiConfig.PASSWORD_GRANT_TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@SecurityRequirement(name = PASSWORD_GRANT_TYPE)
public @interface ApiSecuredPasswordGrant {
}
