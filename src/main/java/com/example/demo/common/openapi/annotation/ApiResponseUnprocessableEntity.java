package com.example.demo.common.openapi.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
//@ApiResponse(
//        responseCode = "422",
//        description = "The given request body is not valid, or if given products not found or not active",
//        content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
//)
public @interface ApiResponseUnprocessableEntity {
    /**
     * @return Circumstances of this response
     */
    String value();
}
