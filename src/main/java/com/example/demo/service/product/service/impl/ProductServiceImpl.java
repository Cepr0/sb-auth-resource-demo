package com.example.demo.service.product.service.impl;

import com.example.demo.common.handler.exception.ApiException;
import com.example.demo.service.product.dto.ProductRequest;
import com.example.demo.service.product.dto.ProductResponse;
import com.example.demo.service.product.mapper.ProductMapper;
import com.example.demo.service.product.model.Product;
import com.example.demo.service.product.repo.ProductRepo;
import com.example.demo.service.product.service.ProductService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepo productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse create(@NonNull ProductRequest productRequest) {
        Product saved = productRepo.save(productMapper.toEntity(productRequest));
        log.info("Product was saved successfully: {}", saved);
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(@NonNull Long id) {
        return productRepo.findById(id).map(product -> {
            log.info("Found product: {}", product);
            return productMapper.toResponse(product);
        }).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            throw ApiException.notFound("product.not-found-by-id", id);
        });
    }

    @Override
    public ProductResponse updateById(@NonNull ProductRequest productRequest, @NonNull Long id) {
        return productRepo.findById(id).map(product -> {
            productMapper.update(productRequest, product);
            log.info("Product with id={} was updated to: {}", id, product);
            return productMapper.toResponse(product);
        }).orElseThrow(() -> {
            log.info("Product with id {} not found", id);
            throw ApiException.notFound("product.not-found-by-id", id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getByBrandId(@NonNull Long brandId, @NonNull Pageable pageable) {
        Page<Product> products = productRepo.findAllByBrandId(brandId, pageable);
        log.info("Found products by brandId: {} received {} items", brandId, products.getTotalElements());
        return products.map(productMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getByManyId(@NonNull List<Long> id) {
        List<Product> products = productRepo.findByIdIn(id);
        log.info("Found products by many id: {} received {} items", id, products.size());
        return products.stream().map(productMapper::toResponse).collect(Collectors.toList());
    }
}
