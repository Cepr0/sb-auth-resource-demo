package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(responseCode = "204", description = "")
public @interface ApiResponseNoContent {
    /**
     * @return Circumstances of this response
     */
    String value();
}
