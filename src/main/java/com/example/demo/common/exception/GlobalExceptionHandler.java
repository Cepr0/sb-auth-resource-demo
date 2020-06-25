package com.example.demo.common.exception;

import com.example.demo.common.i18n.MessageProvider;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Slf4j
@ControllerAdvice
@Configuration
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_FAILED = "validation.failed";

    private final ApiErrorMappers apiErrorMappers = new ApiErrorMappers();
    private final ApiErrorMessageFactory apiErrorMessageFactory;
    private final MessageProvider mp;

    public GlobalExceptionHandler(ApiErrorMessageFactory apiErrorMessageFactory, MessageProvider mp) {
        this.apiErrorMessageFactory = apiErrorMessageFactory;
        this.mp = mp;
    }

    @Autowired
    protected void initMappers(Map<String, GlobalExceptionHandlerConfigurer> configurerBeans) {
        if (!CollectionUtils.isEmpty(configurerBeans)) {
            Collection<GlobalExceptionHandlerConfigurer> configurers = new TreeMap<>(configurerBeans).values();
            for (GlobalExceptionHandlerConfigurer configurer : configurers) {
                configurer.putApiErrorMappers(apiErrorMappers, apiErrorMessageFactory);
            }
        }
    }

    @Configuration("_defaultGlobalExceptionHandlerConfig")
    protected static class DefaultConfig implements GlobalExceptionHandlerConfigurer {

        @Bean
        MessageProvider messageProvider(MessageSource messageSource) {
            return new MessageProvider(messageSource);
        }

        @Bean
        ApiErrorMessageFactory apiErrorMessageFactory(MessageProvider messageProvider) {
            return new ApiErrorMessageFactory(messageProvider);
        }

        /**
         * Puts default mappings to {@link ApiErrorMessage} for the following exceptions:
         * <ul>
         * 	<li>{@link ApiException}
         * 	<li>{@link MethodArgumentNotValidException}
         * 	<li>{@link ConstraintViolationException}
         * 	<li>{@link ValidationException}
         *
         * @param mappers {@link ApiErrorMappers} must not be null
         */
        @Override
        public void putApiErrorMappers(ApiErrorMappers mappers, ApiErrorMessageFactory apiError) {

            mappers.put(ApiException.class, ex -> apiError.build(ex.getMessage(), ex.getHttpStatus(), ex.getArgs()));

            mappers.put(MethodArgumentNotValidException.class, ex -> {
                ApiErrorMessage errorMessage = apiError.unprocessableEntity(VALIDATION_FAILED);
                ex.getBindingResult().getAllErrors().forEach(error -> errorMessage.addError(apiError.error(error)));
                return errorMessage;
            });

            mappers.put(ConstraintViolationException.class, ex -> {
                ApiErrorMessage errorMessage = apiError.unprocessableEntity(VALIDATION_FAILED);
                ex.getConstraintViolations().forEach(error ->
                        errorMessage.addError(apiError.error(
                                error.getRootBeanClass().getSimpleName(),
                                ((PathImpl) error.getPropertyPath()).getLeafNode().asString(),
                                error.getMessage(),
                                error.getInvalidValue()
                        ))
                );
                return errorMessage;
            });

            mappers.put(ValidationException.class, ex -> {
                ApiErrorMessage errorMessage = apiError.unprocessableEntity(VALIDATION_FAILED);
                ex.getErrors().getAllErrors().forEach(error -> errorMessage.addError(apiError.error(error)));
                return errorMessage;
            });
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            @NonNull WebRequest request
    ) {
        ApiErrorMessage apiErrorMessage = apiErrorMappers.lookupApiErrorMessage(ex).map(errorMessage -> {
            log(ex, (ServletWebRequest) request, null);
            return errorMessage;
        }).orElseGet(() -> {
            log(ex, (ServletWebRequest) request, "Not overridden");
            return apiErrorMessageFactory.build(ex.getMessage(), status);
        });
        return new ResponseEntity<>(apiErrorMessage, headers, apiErrorMessage.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(@NonNull Exception ex, @NonNull ServletWebRequest request) {
        ApiErrorMessage apiErrorMessage = apiErrorMappers.lookupApiErrorMessage(ex).map(aem -> {
            log(ex, request, null);
            return aem;
        }).orElseGet(() -> {
            log(ex, request, "Unhandled");
            return apiErrorMessageFactory.internalServerError(ex.getMessage());
        });
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.getHttpStatus());
    }

    private void log(@NonNull Exception ex, @NonNull ServletWebRequest request, @Nullable String warnMessage) {
        HttpMethod method = request.getHttpMethod();
        String path = request.getRequest().getRequestURI();
        String exStr = Optional.ofNullable(ex.getMessage())
                .map(message -> String.join(": ", ex.getClass().getName(), mp.getMessage(message)))
                .orElseGet(() -> ex.getClass().getName());

        Throwable cause = NestedExceptionUtils.getMostSpecificCause(ex);
        String causeStr = !ex.equals(cause) ? "Cause: " + cause.toString() : "";
        String logMessage;
        if (warnMessage != null) {
            logMessage = String.format("[!] %s exception in %s %s: %s; %s", warnMessage, method, path, exStr, causeStr);
            log.error(logMessage, ex);
        } else {
            logMessage = String.format("Exception in %s %s: %s; %s", method, path, exStr, causeStr);
            if (log.isDebugEnabled()) {
                log.debug("[d] " + logMessage, ex);
            } else {
                log.warn("[w] " + logMessage);
            }
        }
    }

    public static class ApiErrorMappers {
        private final Map<Class<? extends Exception>, Function<? extends Exception, ApiErrorMessage>> mappers = new ConcurrentHashMap<>();

        public <E extends Exception> ApiErrorMappers put(@NonNull Class<E> exceptionType, @NonNull Function<E, ApiErrorMessage> mapper) {
            mappers.put(exceptionType, mapper);
            return this;
        }

        private <E extends Exception> Optional<ApiErrorMessage> lookupApiErrorMessage(@NonNull E exceptionType) {
            //noinspection unchecked
            Function<E, ApiErrorMessage> mapper = (Function<E, ApiErrorMessage>) mappers.get(exceptionType.getClass());
            if (mapper != null) {
                return Optional.of(mapper.apply(exceptionType));
            } else {
                return Optional.empty();
            }
        }
    }
}
