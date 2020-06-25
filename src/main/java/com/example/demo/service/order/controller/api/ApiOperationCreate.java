package com.example.demo.service.order.controller.api;

import com.example.demo.common.openapi.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiSecuredPasswordGrant
@Operation(
        summary = "Creates a new Order",
        description = "Creates a new Order and sets its status to 'NEW'.\n\n" +
                "Checks if given products are present in the DB and they are active."
)
@ApiHeaderAcceptLanguage
@ApiResponseCreated(object = "Order", location = "/orders/{id}")
@ApiResponseNotFound("When the authenticated customer not found")
@ApiResponseUnprocessableEntity("When the given request body is not valid, or if given products not found or not active")
public @interface ApiOperationCreate {
}
