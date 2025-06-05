package com.br.order.unit.infrastructure.provider;

import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.product.ProductResponse;
import com.br.order.infrastructure.provider.OrderHttpRestClient;
import com.br.order.infrastructure.provider.OrderProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class OrderProviderTest {
    @Mock
    private OrderHttpRestClient restClient;

    @InjectMocks
    private OrderProviderImpl orderProvider;

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
                                .price(BigDecimal.valueOf(15))
                                .quantity(2)
                                .build()
                ))
                .total(BigDecimal.valueOf(30))
                .build();
    }

    @Test
    void shouldCallRestClientToSendOrder() {
        orderProvider.receiveOrder(sampleOrder);

        verify(restClient, times(1)).receiveOrder(sampleOrder);
    }
}
