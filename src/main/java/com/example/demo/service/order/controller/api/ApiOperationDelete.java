package com.example.demo.service.order.controller.api;

import com.example.demo.common.openapi.annotation.ApiHeaderAcceptLanguage;
import com.example.demo.common.openapi.annotation.ApiResponseNoContent;
import com.example.demo.common.openapi.annotation.ApiResponseNotFound;
import com.example.demo.common.openapi.annotation.ApiSecuredPasswordGrant;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiSecuredPasswordGrant
@Operation(summary = "Delete the Order by its id")
@ApiHeaderAcceptLanguage
@ApiResponseNoContent("In case of successful operation")
@ApiResponseNotFound("When the authenticated customer not found or when the order bot found")
public @interface ApiOperationDelete {
}
