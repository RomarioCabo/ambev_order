package com.br.order.unit.infrastructure.persistence.order;

import com.br.order.domain.enums.Status;
import com.br.order.domain.order.OrderResponse;
import com.br.order.infrastructure.persistence.order.OrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class OrderEntityTest {

    @Test
    void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        BigDecimal total = BigDecimal.valueOf(100);
        Status status = Status.COMPLETED;
        LocalDateTime createdAt = LocalDateTime.now();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(id)
                .total(total)
                .status(status)
                .createdAt(createdAt)
                .build();

        assertEquals(id, orderEntity.getId());
        assertEquals(total, orderEntity.getTotal());
        assertEquals(status, orderEntity.getStatus());
        assertEquals(createdAt, orderEntity.getCreatedAt());
    }

    @Test
    void testConstructorFromOrderResponse() {
        UUID id = UUID.randomUUID();
        BigDecimal total = BigDecimal.valueOf(250);
        Status status = Status.COMPLETED;

        OrderResponse orderResponse = OrderResponse.builder()
                .id(id)
                .total(total)
                .status(status)
                .build();

        OrderEntity entity = new OrderEntity(orderResponse);

        assertEquals(id, entity.getId());
        assertEquals(total, entity.getTotal());
        assertEquals(status, entity.getStatus());

        assertNotNull(entity.getCreatedAt());
        // Check createdAt is near now (within a few seconds)
        assertTrue(entity.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
        assertTrue(entity.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(10)));
    }
}
