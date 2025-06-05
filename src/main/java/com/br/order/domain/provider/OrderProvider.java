package com.br.order.domain.provider;

import com.br.order.domain.order.OrderResponse;

public interface OrderProvider {
    void receiveOrder(OrderResponse request);
}
