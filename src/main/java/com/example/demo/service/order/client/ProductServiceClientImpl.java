package com.example.demo.service.order.client;

import com.example.demo.common.exception.ApiException;
import com.example.demo.service.order.dto.productService.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
//https://stackoverflow.com/questions/53150345/spring-retry-cannot-locate-recovery-method-when-implement-an-interface
@EnableRetry(proxyTargetClass = true)
@Service
public class ProductServiceClientImpl implements ProductServiceClient {
    private final RestTemplate productApi;

    public ProductServiceClientImpl(RestTemplate productApi) {
        this.productApi = productApi;
    }

    @Override
    @Retryable(
            value = {ResourceAccessException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000))
    public List<ProductResponse> findByIds(List<Long> ids) {
        final ProductResponse[] response = productApi.getForObject("/products/?id={ids}",
                ProductResponse[].class,
                Map.of("ids", ids.toArray()));
        return response != null ? Arrays.asList(response) : Collections.emptyList();
    }

    @Recover
    public List<ProductResponse> recover(ResourceAccessException e) {
        log.error("Can't establish connection with product-service.", e);
        throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "service.unavailable");
    }
}
