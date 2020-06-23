package com.example.demo.service.order.controller;

import com.example.demo.common.handler.exception.ApiErrorMessage;
import com.example.demo.common.openapi.annotation.ApiResponseCreated;
import com.example.demo.common.openapi.annotation.ApiResponseNotFound;
import com.example.demo.common.openapi.annotation.ApiResponseUnprocessableEntity;
import com.example.demo.common.openapi.annotation.ApiSecured;
import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.OrderResponse;
import com.example.demo.service.order.model.OrderStatus;
import com.example.demo.service.order.service.OrderService;
import com.example.demo.service.order.validator.OrderRequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.example.demo.common.openapi.OpenApiConfig.SECURITY_SCHEMA;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Tag(name = "Order API", description = "Order related operations")
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService ordersService;
    private final OrderRequestValidator orderRequestValidator;

    public OrderController(
            OrderService ordersService,
            OrderRequestValidator orderRequestValidator
    ) {
        this.ordersService = ordersService;
        this.orderRequestValidator = orderRequestValidator;
    }

    @InitBinder
    private void dataBinding(WebDataBinder binder) {
        binder.addValidators(orderRequestValidator);
    }

    @Operation(
            summary = "Creates a new Order",
            description = "Creates a new Order and sets its status to 'NEW'.\n\n" +
                    "Checks if given products are present in the DB and they are active."
    )
    @Parameter(
            in = ParameterIn.HEADER,
            name = HttpHeaders.ACCEPT_LANGUAGE,
            schema = @Schema(allowableValues = {"en", "uk", "ru"}, defaultValue = "en"),
            description = "Customer languages"
    )
    @ApiSecured
    @ApiResponseCreated("Order")
    @ApiResponseNotFound("Authenticated customer")
    @ApiResponseUnprocessableEntity
    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody OrderRequest request,
            Principal principal
    ) {
        String customerEmail = principal.getName();
        OrderResponse order = ordersService.create(request, customerEmail);
        return ResponseEntity.created(fromUriString("/orders/{id}").build(order.getId())).body(order);
    }

    @Operation(
            security = @SecurityRequirement(name = SECURITY_SCHEMA),
            summary = "Replace the existing Order",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "A replaced Order object",
                            content = @Content(schema = @Schema(implementation = OrderResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If the Order wasn't found",
                            content = @Content(schema = @Schema(implementation = ApiErrorMessage.class))
                    )
            }
    )
    @PutMapping("/{orderId}")
    public OrderResponse replace(
            @PathVariable @Schema(example = "1") long orderId,
            @Valid @RequestBody OrderRequest request
    ) {
        return ordersService.replace(orderId, request);
    }

    @Operation(security = @SecurityRequirement(name = SECURITY_SCHEMA))
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long orderId) {
        ordersService.delete(orderId);
    }

    @Operation(security = @SecurityRequirement(name = SECURITY_SCHEMA))
    @GetMapping("/{orderId}")
    public OrderResponse get(@PathVariable long orderId) {
        return ordersService.get(orderId);
    }

    @Operation(security = @SecurityRequirement(name = SECURITY_SCHEMA))
    @PageableAsQueryParam
    @GetMapping
    public Page<OrderResponse> get(
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 20) @Parameter(hidden = true) Pageable pageable
    ) {
        return ordersService.get(status, pageable);
    }
}
