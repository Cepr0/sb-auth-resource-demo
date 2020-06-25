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
       responseCode = "422",
       description = "",
       content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
)
public @interface ApiResponseUnprocessableEntity {
    /**
     * @return Circumstances of this response
     */
    String value();
}
