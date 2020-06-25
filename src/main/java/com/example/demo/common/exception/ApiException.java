package com.example.demo.common.exception;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import static org.springframework.http.HttpStatus.*;

public class ApiException extends RuntimeException {

    @Getter private final HttpStatus httpStatus;
    @Getter private final Object[] args;

    public ApiException(@NonNull HttpStatus httpStatus, @NonNull String codeOrMessage, @Nullable Object... args) {
        super(codeOrMessage);
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public ApiException(@NonNull Throwable cause, @NonNull HttpStatus httpStatus, @NonNull String codeOrMessage, @Nullable Object... args) {
        super(codeOrMessage, cause);
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public static ApiException notFound(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(NOT_FOUND, codeOrMessage, args);
    }

    public static ApiException conflict(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(CONFLICT, codeOrMessage, args);
    }

    public static ApiException badRequest(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(BAD_REQUEST, codeOrMessage, args);
    }

    public static ApiException internalServerError(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(INTERNAL_SERVER_ERROR, codeOrMessage, args);
    }

    public static ApiException unprocessableEntity(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(UNPROCESSABLE_ENTITY, codeOrMessage, args);
    }

    public static ApiException forbidden(@NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(FORBIDDEN, codeOrMessage, args);
    }

    public static ApiException notFound(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, NOT_FOUND, codeOrMessage, args);
    }

    public static ApiException conflict(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, CONFLICT, codeOrMessage, args);
    }

    public static ApiException badRequest(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, BAD_REQUEST, codeOrMessage, args);
    }

    public static ApiException internalServerError(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, INTERNAL_SERVER_ERROR, codeOrMessage, args);
    }

    public static ApiException unprocessableEntity(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, UNPROCESSABLE_ENTITY, codeOrMessage, args);
    }

    public static ApiException forbidden(@NonNull Throwable cause, @NonNull String codeOrMessage, @Nullable Object... args) {
        return new ApiException(cause, FORBIDDEN, codeOrMessage, args);
    }
}
