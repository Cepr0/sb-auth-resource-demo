package com.example.demo.service.order.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {
    @Min(1)
    private long productId;

    @Schema(pattern = "###.00", format = "float")
    @Digits(integer = 11, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal price;

    @Min(1)
    int amount;
}
