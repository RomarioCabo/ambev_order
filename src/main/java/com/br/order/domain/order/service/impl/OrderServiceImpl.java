package com.br.order.domain.order.service.impl;

import com.br.order.domain.DatabaseProvider;
import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DatabaseProvider databaseProvider;

    @Override
    public boolean existsOrderById(UUID orderId) {
        return databaseProvider.existsOrderById(orderId);
    }

    @Override
    public OrderResponse calculateValueOfProducts(OrderResponse order) {
        if (order == null || order.getProducts().isEmpty()) {
            return null;
        }

        BigDecimal total = order.getProducts().stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total);
        return order;
    }

    @Override
    public void save(OrderResponse order) {
        databaseProvider.saveOrder(order);
    }
}
