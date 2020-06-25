package com.example.demo.common.openapi.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
//@ApiResponse(
//        responseCode = "201",
//        description = "A new Order object",
//        headers = @Header(name = HttpHeaders.LOCATION, description = "Contains the order id: '/orders/{id}'")
//)
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
