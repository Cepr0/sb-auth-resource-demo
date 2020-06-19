package com.example.demo.service.order.dto;

import com.example.demo.common.dto.BaseResponse;
import com.example.demo.service.order.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse implements BaseResponse<Long> {
    private Long id;
    private OrderStatus status;
    @JsonProperty("products") private List<OrderItemResponse> orderItems;
}
