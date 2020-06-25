package com.example.demo.common.openapi.annotation;

import com.example.demo.common.exception.ApiErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(
       responseCode = "404",
       description = "",
       content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
)
public @interface ApiResponseBadRequest {
    /**
     * @return Circumstances of this response
     */
    String value();
}
