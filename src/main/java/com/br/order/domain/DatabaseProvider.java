package com.br.order.domain;

import com.br.order.domain.order.OrderResponse;

import java.util.UUID;

public interface DatabaseProvider {
    boolean existsOrderById(UUID orderId);

    void saveOrder(OrderResponse order);
}
