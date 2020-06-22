package com.example.demo.service.order.dto;

import com.example.demo.common.dto.BaseResponse;
import com.example.demo.service.order.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name="OrderResponse", description="Order DTO",
        example = "{\n" +
                "  \"id\": 1,\n" +
                "  \"status\": \"NEW\",\n" +
                "  \"products\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"price\": 123.45,\n" +
                "      \"amount\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "}"
)
@Data
public class OrderResponse implements BaseResponse<Long> {

    private Long id;

    private OrderStatus status;

    @JsonProperty("products")
    @Schema(minimum = "1")
    private List<OrderItemResponse> orderItems;
}
