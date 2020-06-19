package com.example.demo.service.order.client;

import com.example.demo.service.order.dto.productService.ProductResponse;

import java.util.List;

public interface ProductServiceClient {
    List<ProductResponse> findByIds(List<Long> ids);
}
