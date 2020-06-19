package com.example.demo.service.product.service;

import com.example.demo.service.product.dto.BrandRequest;
import com.example.demo.service.product.dto.BrandResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    BrandResponse create(BrandRequest brandRequest);

    BrandResponse getById(Long id);

    void deleteById(Long brandId);

    BrandResponse updateById(BrandRequest brandRequest, Long id);

    Page<BrandResponse> getAll(Pageable pageable);
}
