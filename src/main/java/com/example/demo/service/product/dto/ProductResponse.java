package com.example.demo.service.product.dto;

import com.example.demo.common.dto.BaseResponse;
import com.example.demo.service.product.model.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse implements BaseResponse<Long> {
    private Long id;
    private String name;
    private String description;
    private long amount;
    private BigDecimal price;
    private ProductStatus status;
    @JsonProperty("brandId") private Long brand;
}
