package com.example.demo.common.handler.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Unified error message container which is rendered as:
 * <br/>
 * <pre>
 * {
 *    "timestamp": "2017-10-20T13:36:04.859Z",
 *    "status": 400,
 *    "error": "Bad request",
 *    "message": "Validation failed",
 *    "path": "/path",
 *    "errors": [
 *       {
 *          "object": "Validation",
 *          "property": "conditions[].value1",
 *          "message": "conditions[].value1 must be at least 0!",
 *          "invalidValue": -10
 *       },
 *       {
 *          "object": "Validation",
 *          "property": "conditions[].value2",
 *          "message": "conditions[].value2 must be at least 0!",
 *          "invalidValue": -20
 *       }
 *    ]
 * }
 * </pre>
 */
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({"timestamp", "status", "error", "message", "path", "errors"})
@Value
public class ApiErrorMessage implements Serializable {

    /**
     * Error's timestamp
     */
    Instant timestamp;

    @JsonIgnore
    HttpStatus httpStatus;

    /**
     * Error's HTTP status
     */
    Integer status;

    /**
     * Error's status description
     */
    String error;

    /**
     * Error's detailed message
     */
    @NonNull String message;

    /**
     * Request path related to the error
     */
    String path;

    /**
     * Collection of detailed sub-errors, for example, validation error of the request body specific field
     */
    Collection<Error> errors = new ArrayList<>();

    ApiErrorMessage(@NonNull Instant timestamp, @NonNull HttpStatus httpStatus, @NonNull String message, @NonNull String path) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.status = this.httpStatus.value();
        this.error = this.httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    /**
     * Add {@link Error} to errors collection
     *
     * @param error must not be null
     */
    public void addError(@NonNull Error error) {
        errors.add(error);
    }

    /**
     * Sub-error container
     */
    @JsonInclude(NON_EMPTY)
    @Value
    public static class Error implements Serializable {

        /**
         * Object name related to the sub-error
         */
        String object;

        /**
         * Object property name related to the sub-error
         */
        String property;

        /**
         * Detailed sub-error message
         */
        @NonNull String message;

        /**
         * Invalid value of object property related to the sub-error
         */
        Object invalidValue;
    }
}
