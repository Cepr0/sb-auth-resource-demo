package com.example.demo.service.product.dto;

import com.example.demo.common.dto.BaseResponse;
import lombok.Data;

@Data
public class BrandResponse implements BaseResponse<Long> {
    private Long id;
    private String name;
    private String description;
}
