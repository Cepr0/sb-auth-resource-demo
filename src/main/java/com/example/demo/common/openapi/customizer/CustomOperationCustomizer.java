package com.example.demo.common.openapi.customizer;

import com.example.demo.common.openapi.annotation.*;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@Component
public class CustomOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        customizeApiResponseCreated(operation, handlerMethod);
        customizeApiResponse(OK, operation, handlerMethod);
        customizeApiResponse(NO_CONTENT, operation, handlerMethod);
        customizeApiResponse(NOT_FOUND, operation, handlerMethod);
        customizeApiResponse(BAD_REQUEST, operation, handlerMethod);
        customizeApiResponse(UNPROCESSABLE_ENTITY, operation, handlerMethod);
        return operation;
    }

    private void customizeApiResponse(HttpStatus status, Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = getResponses(operation);
        Annotation annotation;
        String description = null;
        switch (status) {
            case OK:
                annotation = handlerMethod.getMethodAnnotation(ApiResponseOk.class);
                if (annotation != null) {
                    description = ((ApiResponseOk) annotation).value();
                }
                break;
            case NO_CONTENT:
                annotation = handlerMethod.getMethodAnnotation(ApiResponseNoContent.class);
                if (annotation != null) {
                    description = ((ApiResponseNoContent) annotation).value();
                }
                break;
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
            ApiResponse apiResponse = responses.get(String.valueOf(status.value()));
            if (apiResponse != null) {
                apiResponse.setDescription(description);
            }
        }
    }

    private void customizeApiResponseCreated(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = getResponses(operation);
        var annotation = handlerMethod.getMethodAnnotation(ApiResponseCreated.class);
        if (annotation != null) {
            String object = annotation.object();
            String description = "Returns a created " + object + " object";
            String headerDescription = "Contains a created " + object + " id: " + annotation.location();
            ApiResponse apiResponse = responses.get(String.valueOf(CREATED.value()));
            if (apiResponse != null) {
                apiResponse.setDescription(description);
                Map<String, Header> headers = apiResponse.getHeaders();
                if (headers != null) {
                    Header locationHeader = headers.get(HttpHeaders.LOCATION);
                    if (locationHeader != null) {
                        locationHeader.setDescription(headerDescription);
                    }
                }
            }
        }
    }

    private ApiResponses getResponses(Operation operation) {
        ApiResponses responses = operation.getResponses();
        if (responses == null) {
            responses = new ApiResponses();
            operation.setResponses(responses);
        }
        return responses;
    }

}
