package com.br.order.unit.infrastructure.persistence;

import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.product.ProductResponse;
import com.br.order.infrastructure.persistence.DatabaseProviderImpl;
import com.br.order.infrastructure.persistence.order.OrderEntity;
import com.br.order.infrastructure.persistence.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DatabaseProviderTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private DatabaseProviderImpl databaseProvider;

    private OrderResponse sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleOrder = OrderResponse.builder()
                .id(UUID.randomUUID())
                .products(List.of(
                        ProductResponse.builder()
                                .id(UUID.randomUUID())
                                .name("Product A")
                                .price(BigDecimal.valueOf(20))
                                .quantity(1)
                                .build()
                ))
                .total(BigDecimal.valueOf(20))
                .build();
    }

    @Test
    void shouldReturnTrueWhenOrderExistsInRepository() {
        UUID orderId = sampleOrder.getId();
        when(orderRepository.existsById(orderId)).thenReturn(true);

        boolean exists = databaseProvider.existsOrderById(orderId);

        verify(orderRepository).existsById(orderId);
        assert exists;
    }

    @Test
    void shouldReturnFalseWhenOrderDoesNotExistInRepository() {
        UUID orderId = sampleOrder.getId();
        when(orderRepository.existsById(orderId)).thenReturn(false);

        boolean exists = databaseProvider.existsOrderById(orderId);

        verify(orderRepository).existsById(orderId);
        assert !exists;
    }

    @Test
    void shouldSaveOrderEntityToRepository() {
        databaseProvider.saveOrder(sampleOrder);

        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderRepository).save(captor.capture());

        OrderEntity savedEntity = captor.getValue();
        assert savedEntity != null;
        assert savedEntity.getId().equals(sampleOrder.getId());
    }
}
