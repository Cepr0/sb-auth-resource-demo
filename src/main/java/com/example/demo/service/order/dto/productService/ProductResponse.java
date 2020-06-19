package com.example.demo.service.order.dto.productService;

import lombok.Data;

import java.time.Instant;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private long amount;
    private String price;
    private ProductStatus status;
    private Long brandId;
}
