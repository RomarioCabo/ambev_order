package com.br.order.unit.domain.order.service;

import com.br.order.domain.DatabaseProvider;
import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.order.service.impl.OrderServiceImpl;
import com.br.order.domain.product.ProductResponse;
import com.br.order.domain.provider.OrderProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private DatabaseProvider databaseProvider;

    @Mock
    private OrderProvider orderProvider;

    @InjectMocks
    private OrderServiceImpl orderService;

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
                                .price(BigDecimal.valueOf(10))
                                .quantity(2)
                                .build(),
                        ProductResponse.builder()
                                .id(UUID.randomUUID())
                                .name("Product B")
                                .price(BigDecimal.valueOf(5))
                                .quantity(3)
                                .build()
                ))
                .build();
    }

    @Test
    void shouldReturnTrueWhenOrderExists() {
        UUID orderId = UUID.randomUUID();
        when(databaseProvider.existsOrderById(orderId)).thenReturn(true);

        boolean exists = orderService.existsOrderById(orderId);

        assertTrue(exists);
        verify(databaseProvider).existsOrderById(orderId);
    }

    @Test
    void shouldReturnFalseWhenOrderDoesNotExist() {
        UUID orderId = UUID.randomUUID();
        when(databaseProvider.existsOrderById(orderId)).thenReturn(false);

        boolean exists = orderService.existsOrderById(orderId);

        assertFalse(exists);
        verify(databaseProvider).existsOrderById(orderId);
    }

    @Test
    void shouldCalculateTotalValueCorrectly() {
        OrderResponse result = orderService.calculateValueOfProducts(sampleOrder);

        BigDecimal expectedTotal = BigDecimal.valueOf(10 * 2 + 5 * 3); // 35
        assertNotNull(result);
        assertEquals(expectedTotal, result.getTotal());
    }

    @Test
    void shouldReturnNullIfOrderIsNull() {
        assertNull(orderService.calculateValueOfProducts(null));
    }

    @Test
    void shouldReturnNullIfOrderProductsIsEmpty() {
        OrderResponse emptyOrder = OrderResponse.builder()
                .id(UUID.randomUUID())
                .products(List.of())
                .build();

        assertNull(orderService.calculateValueOfProducts(emptyOrder));
    }

    @Test
    void shouldSaveOrderUsingDatabaseProvider() {
        orderService.save(sampleOrder);
        verify(databaseProvider).saveOrder(sampleOrder);
    }

    @Test
    void shouldForwardOrderToOrderProvider() {
        orderService.receiveOrder(sampleOrder);
        verify(orderProvider).receiveOrder(sampleOrder);
    }
}
