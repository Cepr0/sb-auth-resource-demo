package com.example.demo.service.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.OrderResponse;
import com.example.demo.service.order.model.OrderStatus;

public interface OrderService {
    OrderResponse create(OrderRequest request, String customerEmail);

    OrderResponse replace(long orderId, OrderRequest orderRequest);

    void delete(long orderId);

    OrderResponse get(long orderId);

    Page<OrderResponse> get(OrderStatus status, Pageable pageable);
}
