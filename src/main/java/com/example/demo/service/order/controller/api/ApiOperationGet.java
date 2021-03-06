package com.example.demo.service.order.controller.api;

import com.example.demo.common.openapi.annotation.ApiHeaderAcceptLanguage;
import com.example.demo.common.openapi.annotation.ApiResponseNotFound;
import com.example.demo.common.openapi.annotation.ApiResponseOk;
import com.example.demo.common.openapi.annotation.ApiSecuredPasswordGrant;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiSecuredPasswordGrant
@Operation(summary = "Get the Order by its id")
@ApiHeaderAcceptLanguage
@ApiResponseOk("The Order object")
@ApiResponseNotFound("When the authenticated customer not found or when the order bot found")
public @interface ApiOperationGet {
}
