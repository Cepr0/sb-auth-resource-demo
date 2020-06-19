package com.example.demo.service.order.validator;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.example.demo.service.order.client.ProductServiceClient;
import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.productService.ProductResponse;
import com.example.demo.service.order.dto.productService.ProductStatus;
import com.example.demo.service.order.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderRequestValidator implements Validator {
    private final ProductServiceClient productServiceClient;

    public OrderRequestValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return OrderRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        OrderRequest orderRequest = (OrderRequest) target;
        List<Long> requestedProducts = orderRequest.getOrderItems().stream()
                .map(OrderItem::getProductId)
                .collect(Collectors.toList());
        List<ProductResponse> foundProducts = productServiceClient.findByIds(requestedProducts);

        if (requestedProducts.size() != foundProducts.size()) {
            errors.reject("validation.failed.product.not_found");
        }

        foundProducts.stream()
                .filter(product -> product.getStatus() != ProductStatus.ACTIVE)
                .findAny().ifPresent(product -> errors.reject("validation.failed.product.invalid_status"));
    }
}
