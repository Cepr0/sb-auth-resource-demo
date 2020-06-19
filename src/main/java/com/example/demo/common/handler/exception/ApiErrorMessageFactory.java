package com.example.demo.common.handler.exception;

import com.example.demo.common.i18n.MessageProvider;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Clock;
import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

public class ApiErrorMessageFactory {

    private final MessageProvider mp;
    Clock clock;

    public ApiErrorMessageFactory(MessageProvider mp) {
        this.mp = mp;
        this.clock = Clock.systemUTC();
    }

    public ApiErrorMessage internalServerError(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, INTERNAL_SERVER_ERROR, args);
    }

    public ApiErrorMessage badRequest(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, BAD_REQUEST, args);
    }

    public ApiErrorMessage notFound(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, NOT_FOUND, args);
    }

    public ApiErrorMessage conflict(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, CONFLICT, args);
    }

    public ApiErrorMessage unprocessableEntity(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, UNPROCESSABLE_ENTITY, args);
    }

    public ApiErrorMessage forbidden(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, FORBIDDEN, args);
    }

    public ApiErrorMessage unauthorized(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, UNAUTHORIZED, args);
    }

    public ApiErrorMessage methodNotAllowed(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, METHOD_NOT_ALLOWED, args);
    }

    public ApiErrorMessage notAcceptable(@NonNull String codeOrMessage, @Nullable Object... args) {
        return build(codeOrMessage, METHOD_NOT_ALLOWED, args);
    }

    public ApiErrorMessage.Error error(@NonNull String codeOrMessage) {
        return new ApiErrorMessage.Error(null, null, mp.getLocalizedMessage(codeOrMessage), null);
    }

    public ApiErrorMessage.Error error(@NonNull String codeOrMessage, String object, String property, Object invalidValue) {
        return new ApiErrorMessage.Error(object, property, mp.getLocalizedMessage(codeOrMessage), invalidValue);
    }

    public ApiErrorMessage.Error error(@NonNull ObjectError err) {
        String localizedMessage = mp.getLocalizedMessage(err);
        if (err instanceof FieldError) {
            return new ApiErrorMessage.Error(err.getObjectName(), ((FieldError) err).getField(), localizedMessage, ((FieldError) err).getRejectedValue());
        } else {
            return new ApiErrorMessage.Error(err.getObjectName(), null, localizedMessage, null);
        }
    }

    ApiErrorMessage build(String codeOrMessage, HttpStatus httpStatus, @Nullable Object... args) {
        Instant timestamp = Instant.now(clock);
        var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String path = attributes.getRequest().getRequestURI();
        String message = mp.getLocalizedMessage(codeOrMessage, args);
        return new ApiErrorMessage(timestamp, httpStatus, message, path);
    }
}
