package com.example.demo.common.openapi.customizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Schema(
        name = "AuthError",
        description = "Authentication error object",
        example = "{\n" +
                "  \"error\": \"unauthorized\",\n" +
                "  \"error_description\": \"Full authentication is required to access this resource\"\n" +
                "}"
)
@Value
public class AuthError {
    String error;
    @JsonProperty("error_description") String errorDescription;
}
