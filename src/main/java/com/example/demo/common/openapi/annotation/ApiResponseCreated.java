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
