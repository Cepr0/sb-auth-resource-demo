package com.example.demo.service.product.service.impl;

import com.example.demo.common.handler.exception.ApiException;
import com.example.demo.service.product.dto.BrandRequest;
import com.example.demo.service.product.dto.BrandResponse;
import com.example.demo.service.product.mapper.BrandMapper;
import com.example.demo.service.product.model.Brand;
import com.example.demo.service.product.repo.BrandRepo;
import com.example.demo.service.product.service.BrandService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepo brandRepo, BrandMapper brandMapper) {
        this.brandRepo = brandRepo;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandResponse create(@NonNull BrandRequest brandRequest) {
        Brand saved = brandRepo.save(brandMapper.toEntity(brandRequest));
        log.info("Brand was saved successfully: {}", saved);
        return brandMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getById(@NonNull Long id) {
        return brandRepo.findById(id)
                .map(brand -> {
                    log.info("Found Brand is: {}", brand);
                    return brandMapper.toResponse(brand);
                }).orElseThrow(() -> {
                    log.info("Brand with id {} not found", id);
                    throw ApiException.notFound("brand.not-found-by-id", id);
                });
    }

    @Override
    public void deleteById(@NonNull Long id) {
        try {
            brandRepo.deleteById(id);
            log.info("Brand with id={} was deleted", id);
        } catch (EmptyResultDataAccessException e) {
            log.info("Brand id {} not found for delete", id);
            throw ApiException.notFound("brand.not-found-by-id", id);
        }
    }

    @Override
    public BrandResponse updateById(@NonNull BrandRequest brandRequest, @NonNull Long id) {
        return brandRepo.findById(id).map(brand -> {
            brandMapper.update(brandRequest, brand);
            log.info("Brand with id; {} was updated to: {}", id, brand);
            return brandMapper.toResponse(brand);
        }).orElseThrow(() -> {
            log.info("Brand with id {} not found", id);
            throw ApiException.notFound("brand.not-found-by-id", id);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandResponse> getAll(@NonNull Pageable pageable) {
        Page<Brand> brands = brandRepo.findAll(pageable);
        log.info("Found Brands. Received {} items", brands.getTotalElements());
        return brands.map(brandMapper::toResponse);
    }
}
