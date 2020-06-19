package com.example.demo.service.product.service;

import com.example.demo.service.product.dto.ProductRequest;
import com.example.demo.service.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);

    ProductResponse getById(Long id);

    ProductResponse updateById(ProductRequest productRequest, Long id);

    Page<ProductResponse> getByBrandId(Long brandId, Pageable pageable);

    List<ProductResponse> getByManyId(List<Long> id);
}
