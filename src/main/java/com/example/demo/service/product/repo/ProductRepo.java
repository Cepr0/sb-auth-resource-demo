package com.example.demo.service.product.repo;

import com.example.demo.service.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByBrandId(Long brandId, Pageable pageable);

    List<Product> findByIdIn(List<Long> id);
}
