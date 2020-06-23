package com.example.demo.common.openapi.annotation;

import com.example.demo.common.handler.exception.ApiErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(
        responseCode = "422",
        description = "The given request body is not valid, or if given products not found or not active",
        content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
)
public @interface ApiResponseUnprocessableEntity {
    /**
     * @return A description of invalidation
     */
    String value() default "";
}
