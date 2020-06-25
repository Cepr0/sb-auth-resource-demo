package com.example.demo.service.order.controller.api;

import com.example.demo.common.openapi.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiSecuredPasswordGrant
@Operation(summary = "Replaces the existing Order")
@ApiHeaderAcceptLanguage
@ApiResponseOk("Return a replaced Order")
@ApiResponseNotFound("When the authenticated customer not found or when the order bot found")
@ApiResponseUnprocessableEntity("When the given request body is not valid, or if given products not found or not active")
public @interface ApiOperationReplace {
}
