package com.example.demo.service.product.mapper;

import com.example.demo.common.mapper.BaseMapper;
import com.example.demo.common.mapper.NotNullChecker;
import com.example.demo.service.product.dto.ProductRequest;
import com.example.demo.service.product.dto.ProductResponse;
import com.example.demo.service.product.model.Product;
import com.example.demo.service.product.repo.BrandRepo;
import lombok.NonNull;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(config = BaseMapper.class, uses = {NotNullChecker.class, BrandRepo.class})
public abstract class ProductMapper extends BaseMapper<Product, Long, ProductRequest, ProductResponse> {

    BigDecimal priceToString(@NonNull long price) {
        return BigDecimal.valueOf(price).movePointLeft(2);
    }

    long priceToLong(@NonNull BigDecimal price) {
        return price.movePointRight(2).longValue();
    }

}
