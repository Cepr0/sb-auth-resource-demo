package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;

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
       responseCode = "201",
       description = "",
       headers = @Header(name = HttpHeaders.LOCATION, description = "")
)
public @interface ApiResponseCreated {
    /**
     * @return A name of the object
     */
    String object();

    /**
     * @return A name of the object
     */
    String location();
}
