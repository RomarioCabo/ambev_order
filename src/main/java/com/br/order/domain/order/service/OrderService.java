package com.br.order.domain.order.service;

import com.br.order.domain.order.OrderResponse;

import java.util.UUID;

public interface OrderService {

    boolean existsOrderById(UUID orderId);

    OrderResponse calculateValueOfProducts(OrderResponse order);

    void save(OrderResponse order);
}
