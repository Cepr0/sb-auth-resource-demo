package com.example.demo.common.openapi.customizer;

import com.example.demo.common.openapi.annotation.ApiResponseBadRequest;
import com.example.demo.common.openapi.annotation.ApiResponseCreated;
import com.example.demo.common.openapi.annotation.ApiResponseNotFound;
import com.example.demo.common.openapi.annotation.ApiResponseUnprocessableEntity;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Component
public class CustomOperationCustomizer implements OperationCustomizer {

    private final Map<HttpStatus, Pair<? extends Annotation>> statusMap = new ConcurrentHashMap<>();

//    public CustomOperationCustomizer() {
//        statusMap.put(NOT_FOUND, new Pair<>(ApiResponseNotFound.class, ApiResponseNotFound::value));
//        statusMap.put(BAD_REQUEST, new Pair<>(ApiResponseBadRequest.class, ApiResponseBadRequest::value));
//        statusMap.put(UNPROCESSABLE_ENTITY, new Pair<>(ApiResponseUnprocessableEntity.class, ApiResponseUnprocessableEntity::value));
//    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        customizeApiResponseCreated(operation, handlerMethod);
        customizeApiError(NOT_FOUND, operation, handlerMethod);
        customizeApiError(BAD_REQUEST, operation, handlerMethod);
        customizeApiError(UNPROCESSABLE_ENTITY, operation, handlerMethod);
        return operation;
    }

    private void customizeApiError(HttpStatus status, Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = operation.getResponses();
        if (responses == null) {
            responses = new ApiResponses();
            operation.setResponses(responses);
        }

        Annotation annotation;
        String description = null;

//        Pair<? extends Annotation> pair = statusMap.get(status);
//        if (pair != null) {
//            annotation = handlerMethod.getMethodAnnotation(pair.getAnnotationType());
//            if (annotation != null) {
//                pair.getValue().apply(annotation);
//            }
//
//        }
        switch (status) {
            case NOT_FOUND:
                annotation = handlerMethod.getMethodAnnotation(ApiResponseNotFound.class);
                if (annotation != null) {
                    description = ((ApiResponseNotFound) annotation).value();
                }
                break;
            case BAD_REQUEST:
                annotation = handlerMethod.getMethodAnnotation(ApiResponseBadRequest.class);
                if (annotation != null) {
                    description = ((ApiResponseBadRequest) annotation).value();
                }
                break;
            case UNPROCESSABLE_ENTITY:
                annotation = handlerMethod.getMethodAnnotation(ApiResponseUnprocessableEntity.class);
                if (annotation != null) {
                    description = ((ApiResponseUnprocessableEntity) annotation).value();
                }
                break;
            default:
                throw new UnsupportedOperationException(format("Custom APIResponse annotation for %s status is not implemented yet", status));
        }

        if (description != null) {
            responses.addApiResponse(String.valueOf(status.value()), new ApiResponse().description(description));
        }
    }

    private void customizeApiResponseCreated(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = operation.getResponses();
        if (responses == null) {
            responses = new ApiResponses();
            operation.setResponses(responses);
        }
        ApiResponseCreated annotation = handlerMethod.getMethodAnnotation(ApiResponseCreated.class);
        if (annotation != null) {
            String object = annotation.object();
            String description = "Return a new " + object + " object";
            String headerDescription = "Contains the " + object + " id: " + annotation.location();
            responses.addApiResponse("201",
                    new ApiResponse()
                            .description(description)
                            .headers(Map.of(HttpHeaders.LOCATION, new Header().description(headerDescription)))
            );
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Pair<T extends Annotation> {
        private final Class<T> annotationType;
        private final Function<T, String> value;
    }
}
