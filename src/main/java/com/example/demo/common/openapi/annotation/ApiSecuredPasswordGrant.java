package com.example.demo.common.openapi.annotation;

import com.example.demo.common.openapi.customizer.AuthError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

import static com.example.demo.common.openapi.OpenApiConfig.PASSWORD_GRANT_TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(
        responseCode = "401",
        description = "In case of unauthorized request",
        content = @Content(schema = @Schema(implementation = AuthError.class))
)
@SecurityRequirement(name = PASSWORD_GRANT_TYPE)
public @interface ApiSecuredPasswordGrant {
}
