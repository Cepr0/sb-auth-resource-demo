package com.example.demo.common.openapi.annotation;

import com.example.demo.common.openapi.customizer.AuthError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.demo.common.openapi.OpenApiConfig.PASSWORD_GRANT_TYPE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Documented
@ApiResponse(
        responseCode = "401",
        description = "In case of unauthorized request",
        content = @Content(schema = @Schema(implementation = AuthError.class))
)
@SecurityRequirement(name = PASSWORD_GRANT_TYPE)
public @interface ApiSecuredPasswordGrant {
}
