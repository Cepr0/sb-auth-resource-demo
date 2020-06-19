package com.example.demo.service.product.mapper;

import com.example.demo.common.mapper.BaseMapper;
import com.example.demo.common.mapper.NotNullChecker;
import com.example.demo.service.product.dto.BrandRequest;
import com.example.demo.service.product.dto.BrandResponse;
import com.example.demo.service.product.model.Brand;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class, uses = NotNullChecker.class)
public abstract class BrandMapper extends BaseMapper<Brand, Long, BrandRequest, BrandResponse> {
}
