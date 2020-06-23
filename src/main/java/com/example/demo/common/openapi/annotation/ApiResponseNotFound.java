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
        responseCode = "404",
        description = "Authenticated customer not found",
        content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
)
public @interface ApiResponseNotFound {
    /**
     * @return A name of not found object
     */
    String value() default "";
}
