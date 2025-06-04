package com.br.order.infrastructure.persistence;

import com.br.order.domain.DatabaseProvider;
import com.br.order.domain.order.OrderResponse;
import com.br.order.infrastructure.persistence.order.OrderEntity;
import com.br.order.infrastructure.persistence.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseProviderImpl implements DatabaseProvider {

    private final OrderRepository orderRepository;

    @Override
    public boolean existsOrderById(UUID orderId) {
        return orderRepository.existsById(orderId);
    }

    @Override
    @Transactional
    public void saveOrder(OrderResponse order) {
        OrderEntity orderEntity = new OrderEntity(order);
        orderRepository.save(orderEntity);
    }
}
