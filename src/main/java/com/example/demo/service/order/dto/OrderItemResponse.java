package com.example.demo.service.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private long productId;
    private BigDecimal price;
    int amount;
}