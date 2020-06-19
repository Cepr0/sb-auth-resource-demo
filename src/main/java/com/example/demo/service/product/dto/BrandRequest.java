package com.example.demo.service.product.dto;

import com.example.demo.common.dto.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BrandRequest implements BaseRequest {

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String description;
}
