package com.br.order.unit.domain.consumer;

import com.br.order.domain.consumer.OrderConsumer;
import com.br.order.domain.enums.Status;
import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.order.service.OrderService;
import com.br.order.domain.product.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderConsumerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderConsumer orderConsumer;

    private OrderResponse order;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        order = OrderResponse.builder()
                .id(UUID.randomUUID())
                .status(Status.COMPLETED)
                .total(BigDecimal.ZERO)
                .products(List.of(
                        ProductResponse.builder()
                                .id(UUID.randomUUID())
                                .name("Product 1")
                                .price(BigDecimal.valueOf(10))
                                .quantity(2)
                                .build()
                ))
                .build();
    }

    @Test
    void shouldConsumeOrderSuccessfully() {
        when(orderService.existsOrderById(order.getId())).thenReturn(false);
        when(orderService.calculateValueOfProducts(order)).thenReturn(order);

        orderConsumer.consume(order);

        verify(orderService).save(order);
        verify(orderService).receiveOrder(order);
        verify(orderService).calculateValueOfProducts(order);
        assert order.getStatus() == Status.COMPLETED;
    }

    @Test
    void shouldNotConsumeIfOrderAlreadyExists() {
        when(orderService.existsOrderById(order.getId())).thenReturn(true);

        orderConsumer.consume(order);

        verify(orderService, never()).calculateValueOfProducts(any());
        verify(orderService, never()).save(any());
        verify(orderService, never()).receiveOrder(any());
    }

    @Test
    void shouldNotConsumeIfOrderCalculationReturnsNull() {
        when(orderService.existsOrderById(order.getId())).thenReturn(false);
        when(orderService.calculateValueOfProducts(order)).thenReturn(null);

        orderConsumer.consume(order);

        verify(orderService, never()).save(any());
        verify(orderService, never()).receiveOrder(any());
    }

    @Test
    void shouldHandleExceptionDuringConsumption() {
        when(orderService.existsOrderById(order.getId())).thenThrow(new RuntimeException("Database down"));

        orderConsumer.consume(order);

        // No exception should be thrown
        verify(orderService, never()).save(any());
        verify(orderService, never()).receiveOrder(any());
    }
}
