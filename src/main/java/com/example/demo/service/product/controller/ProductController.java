package com.example.demo.service.product.controller;

import com.example.demo.service.product.dto.ProductRequest;
import com.example.demo.service.product.dto.ProductResponse;
import com.example.demo.service.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Try to create Product. Input Request: {}", productRequest);
        ProductResponse productResponse = productService.create(productRequest);
        return ResponseEntity.created(fromUriString("/products/{id}").build(productResponse.getId())).body(productResponse);
    }

    @GetMapping("/{id}")
    public ProductResponse findByID(@PathVariable Long id) {
        log.info("Try to get product by id={}", id);
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@Valid @RequestBody ProductRequest productRequest, @PathVariable Long id) {
        log.info("Try to update product with id={} Input request: {}", id, productRequest);
        return productService.updateById(productRequest, id);
    }

    @GetMapping
    public List<ProductResponse> findByIds(@RequestParam List<Long> id) {
        log.info("Try to get products by many id: {}", id);
        return productService.getByManyId(id);
    }
}
