package com.example.demo.service.order.controller;

import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.OrderResponse;
import com.example.demo.service.order.model.OrderStatus;
import com.example.demo.service.order.service.OrderService;
import com.example.demo.service.order.validator.OrderRequestValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.concurrent.Executor;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService ordersService;
    private final Executor taskExecutor;
    private final OrderRequestValidator orderRequestValidator;

    public OrderController(
            OrderService ordersService,
            Executor taskExecutor,
            OrderRequestValidator orderRequestValidator
    ) {
        this.ordersService = ordersService;
        this.taskExecutor = taskExecutor;
        this.orderRequestValidator = orderRequestValidator;
    }

    @InitBinder
    private void dataBinding(WebDataBinder binder) {
        binder.addValidators(orderRequestValidator);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody OrderRequest request,
            Principal principal
    ) {
        String customerEmail = principal.getName();
        OrderResponse order = ordersService.create(request, customerEmail);
        return ResponseEntity.created(fromUriString("/orders/{id}").build(order.getId())).body(order);
//        return supplyAsync(() -> ordersService.create(request, customerEmail), taskExecutor)
//                .thenApply(order -> ResponseEntity.created(fromUriString("/orders/{id}").build(order.getId())).body(order));
    }

    @PutMapping("/{orderId}")
    public OrderResponse replace(
            @PathVariable long orderId,
            @Valid @RequestBody OrderRequest request
    ) {
        return ordersService.replace(orderId, request);
//        return supplyAsync(() -> ordersService.replace(orderId, request), taskExecutor);
    }


    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long orderId) {
        ordersService.delete(orderId);
//        return runAsync(() -> ordersService.delete(orderId), taskExecutor);
    }

    @GetMapping("/{orderId}")
    public OrderResponse get(@PathVariable long orderId) {
        return ordersService.get(orderId);
//        return supplyAsync(() -> ordersService.get(orderId), taskExecutor);
    }

    @GetMapping
    public Page<OrderResponse> get(
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ordersService.get(status, pageable);
//        return supplyAsync(() -> ordersService.get(status, pageable), taskExecutor);
    }
}
