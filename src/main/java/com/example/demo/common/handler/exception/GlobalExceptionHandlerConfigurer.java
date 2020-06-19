package com.example.demo.common.handler.exception;

public interface GlobalExceptionHandlerConfigurer {
    default void putApiErrorMappers(GlobalExceptionHandler.ApiErrorMappers mappers, ApiErrorMessageFactory apiError) {
    }
}
