package com.example.demo.common.openapi.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
//@ApiResponse(
//        responseCode = "404",
//        description = "Authenticated customer not found",
//        content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
//)
public @interface ApiResponseBadRequest {
    /**
     * @return Circumstances of this response
     */
    String value();
}
