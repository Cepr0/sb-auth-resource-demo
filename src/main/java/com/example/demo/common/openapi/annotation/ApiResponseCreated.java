package com.example.demo.common.openapi.annotation;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiResponse(
        responseCode = "201",
        description = "A new Order object",
        headers = @Header(name = HttpHeaders.LOCATION, description = "Contains the order id: '/orders/{id}'")
)
public @interface ApiResponseCreated {
    /**
     * @return A name of the returned object
     */
    String value() default "";
}
