package com.example.demo.service.product.controller;

import com.example.demo.service.product.dto.BrandRequest;
import com.example.demo.service.product.dto.BrandResponse;
import com.example.demo.service.product.dto.ProductResponse;
import com.example.demo.service.product.service.BrandService;
import com.example.demo.service.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Slf4j
@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    private final ProductService productService;

    public BrandController(BrandService brandService, ProductService productService) {
        this.brandService = brandService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BrandRequest brandRequest) {
        log.info("Try to create brand. Input request: {}", brandRequest);
        BrandResponse brandResponse = brandService.create(brandRequest);
        return ResponseEntity.created(fromUriString("/brands/{id}").build(brandResponse.getId())).body(brandResponse);
    }

    @GetMapping
    public Page<BrandResponse> getAll(Pageable pageable) {
        log.info("Try to get brands: {}", pageable);
        return brandService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public BrandResponse getByID(@PathVariable Long id) {
        log.info("Try to get brand by id={}", id);
        return brandService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Try to delete Brand with id={}", id);
        brandService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BrandResponse updateById(@Valid @RequestBody BrandRequest brandRequest, @PathVariable Long id) {
        log.info("Try to update brand with id={} Input request: {}", id, brandRequest);
        return brandService.updateById(brandRequest, id);
    }

    @GetMapping("/{brandId}/products")
    public Page<ProductResponse> findAllProductsByBrandId(@PathVariable Long brandId, Pageable pageable) {
        log.info("Try to get products by Brand id={}", brandId);
        return productService.getByBrandId(brandId, pageable);
    }
}
