package com.example.demo.service.order.dto;

import com.example.demo.common.dto.BaseRequest;
import com.example.demo.service.order.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class OrderRequest implements BaseRequest {
    @NotEmpty
    @Valid
    @JsonProperty("products")
    private Set<OrderItem> orderItems;
}
