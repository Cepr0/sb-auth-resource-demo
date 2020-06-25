package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.*;

import static com.example.demo.common.openapi.OpenApiConfig.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Parameter(
        in = ParameterIn.HEADER,
        name = HttpHeaders.ACCEPT_LANGUAGE,
        schema = @Schema(allowableValues = {LANG_EN, LANG_RU, LANG_UK}, defaultValue = LANG_EN),
        description = "Customer languages"
)
public @interface ApiHeaderAcceptLanguage {
}
