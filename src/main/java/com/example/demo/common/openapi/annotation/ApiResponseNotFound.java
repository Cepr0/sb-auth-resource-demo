package com.example.demo.common.openapi.annotation;

import com.example.demo.common.exception.ApiErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@ApiResponse(
       responseCode = "404",
       description = "",
       content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
)
public @interface ApiResponseNotFound {
    /**
     * @return Circumstances of this response
     */
    String value();
}
