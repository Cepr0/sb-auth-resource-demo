package com.example.demo.service.order.mapper;

import com.example.demo.common.mapper.BaseMapper;
import com.example.demo.common.mapper.NotNullChecker;
import com.example.demo.service.order.dto.OrderRequest;
import com.example.demo.service.order.dto.OrderResponse;
import com.example.demo.service.order.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class, uses = NotNullChecker.class)
public abstract class OrderMapper extends BaseMapper<Order, Long, OrderRequest, OrderResponse> {
}
