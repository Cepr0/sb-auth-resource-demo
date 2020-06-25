package com.example.demo.common.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {

    @Getter
    private final Errors errors;

    public ValidationException(@NonNull String message, @NonNull Errors errors) {
        super(message);
        this.errors = errors;
    }
}
