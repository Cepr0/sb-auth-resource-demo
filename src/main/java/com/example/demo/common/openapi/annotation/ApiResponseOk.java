package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(responseCode = "200", description = "")
public @interface ApiResponseOk {
    /**
     * @return Circumstances of this response
     */
    String value();
}
