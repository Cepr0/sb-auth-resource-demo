package com.example.demo.service.product.dto;

import com.example.demo.common.dto.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductRequest implements BaseRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Min(0)
    private long amount;

    @DecimalMin("0.00")
    @Digits(integer = 11,fraction = 2)
    private BigDecimal price;

    @NotNull
    @JsonProperty("brandId")
    private Long brand;
}
