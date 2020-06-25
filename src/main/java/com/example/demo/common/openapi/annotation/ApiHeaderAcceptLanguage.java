package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.demo.common.openapi.OpenApiConfig.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, ANNOTATION_TYPE})
@Documented
@Parameter(
        in = ParameterIn.HEADER,
        name = HttpHeaders.ACCEPT_LANGUAGE,
        schema = @Schema(allowableValues = {LANG_EN, LANG_RU, LANG_UK}, defaultValue = LANG_EN),
        description = "Customer languages"
)
public @interface ApiHeaderAcceptLanguage {
}
