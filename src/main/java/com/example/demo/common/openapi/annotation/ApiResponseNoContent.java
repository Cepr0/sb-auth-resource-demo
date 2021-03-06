package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Documented
@ApiResponse(responseCode = "204", description = "")
public @interface ApiResponseNoContent {
    /**
     * @return Circumstances of this response
     */
    String value();
}
