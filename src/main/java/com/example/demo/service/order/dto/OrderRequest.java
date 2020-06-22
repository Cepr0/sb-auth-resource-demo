package com.example.demo.service.order.dto;

import com.example.demo.common.dto.BaseRequest;
import com.example.demo.service.order.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Schema(
        name="OrderRequest",
        description="Order object that needs to be created",
        example = "{\n" +
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
public class OrderRequest implements BaseRequest {
    @Schema(minimum = "1")
    @NotEmpty
    @Valid
    @JsonProperty("products")
    private Set<OrderItem> orderItems;
}
