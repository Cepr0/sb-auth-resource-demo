package com.example.demo.common.exception;

public interface GlobalExceptionHandlerConfigurer {
    default void putApiErrorMappers(GlobalExceptionHandler.ApiErrorMappers mappers, ApiErrorMessageFactory apiError) {
    }
}
