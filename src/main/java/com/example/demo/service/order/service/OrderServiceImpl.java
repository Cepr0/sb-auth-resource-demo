package com.example.demo.service.order.service;

import com.example.demo.common.handler.exception.ApiException;
import com.example.demo.service.customer.repo.CustomerRepo;
import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.OrderResponse;
import com.example.demo.service.order.mapper.OrderMapper;
import com.example.demo.service.order.model.Order;
import com.example.demo.service.order.model.OrderStatus;
import com.example.demo.service.order.repo.OrderRepo;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderMapper mapper;
    private final CustomerRepo customerRepo;

    public OrderServiceImpl(OrderRepo orderRepo, OrderMapper mapper, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.mapper = mapper;
        this.customerRepo = customerRepo;
    }

    @Override
    public OrderResponse create(@NonNull OrderRequest request, @NonNull String customerEmail) {
        final Order order = mapper.toEntity(request);
        return customerRepo.getByEmail(customerEmail).map(customer -> {
            order.setCustomer(customer);
            orderRepo.save(order);
            return mapper.toResponse(order);
        }).orElseThrow(() -> ApiException.notFound("customer.not_found", customerEmail));
    }

    @Override
    public OrderResponse replace(long orderId, @NonNull OrderRequest orderRequest) {
        return orderRepo.findById(orderId)
                .filter(Order::isNew)
                .map(order -> mapper.update(orderRequest, order))
                .map(mapper::toResponse)
                .orElseThrow(() -> ApiException.notFound("order.not_found", orderId));
    }

    @Override
    public void delete(long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> ApiException.notFound("order.not_found", orderId));

        List<OrderStatus> allowedStatus = List.of(OrderStatus.NEW, OrderStatus.PENDING);
        if (!allowedStatus.contains(order.getStatus())) {
            throw ApiException.conflict("order.invalid_status_for_operation", allowedStatus);
        }
        order.setStatus(OrderStatus.DELETED);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse get(long orderId) {
        return orderRepo.findById(orderId).map(mapper::toResponse)
                .orElseThrow(() -> ApiException.notFound("order.not_found", orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponse> get(OrderStatus status, Pageable pageable) {
        if (status == null) {
            return orderRepo.findAll(pageable).map(mapper::toResponse);
        } else {
            return orderRepo.getByStatus(status, pageable).map(mapper::toResponse);
        }
    }
}
