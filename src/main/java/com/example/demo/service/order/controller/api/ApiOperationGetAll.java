package com.example.demo.service.order.controller.api;

import com.example.demo.common.openapi.annotation.ApiHeaderAcceptLanguage;
import com.example.demo.common.openapi.annotation.ApiResponseNotFound;
import com.example.demo.common.openapi.annotation.ApiResponseOk;
import com.example.demo.common.openapi.annotation.ApiSecuredPasswordGrant;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.converters.models.PageableAsQueryParam;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@ApiSecuredPasswordGrant
@Operation(summary = "Get pageable Order list its status (optional)")
@ApiHeaderAcceptLanguage
@ApiResponseOk("Pageable Order list")
@ApiResponseNotFound("When the authenticated customer not found")
@PageableAsQueryParam
public @interface ApiOperationGetAll {
}
