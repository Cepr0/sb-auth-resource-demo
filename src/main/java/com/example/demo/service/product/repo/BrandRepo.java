package com.example.demo.service.product.repo;

import com.example.demo.service.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand, Long> {
}
