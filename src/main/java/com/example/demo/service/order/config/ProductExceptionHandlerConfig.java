package com.example.demo.service.order.config;

import com.example.demo.common.handler.exception.ApiErrorMessage;
import com.example.demo.common.handler.exception.ApiErrorMessageFactory;
import com.example.demo.common.handler.exception.GlobalExceptionHandler;
import com.example.demo.common.handler.exception.GlobalExceptionHandlerConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Configuration
public class ProductExceptionHandlerConfig implements GlobalExceptionHandlerConfigurer {

    @Override
    public void putApiErrorMappers(GlobalExceptionHandler.ApiErrorMappers mappers, @NonNull ApiErrorMessageFactory apiError) {
        mappers.put(HttpMessageNotReadableException.class, ex -> apiError.badRequest("request.invalid-body"));

        mappers.put(NoHandlerFoundException.class, ex -> apiError.notFound("request.path-unsupported"));

        mappers.put(MethodArgumentNotValidException.class, ex -> {
            ApiErrorMessage errorMessage = apiError.badRequest("validation.failed");
            ex.getBindingResult().getAllErrors().forEach(error -> errorMessage.addError(apiError.error(error)));
            return errorMessage;
        });

        mappers.put(MethodArgumentTypeMismatchException.class, ex -> apiError.badRequest("validation.failed"));
    }
}
