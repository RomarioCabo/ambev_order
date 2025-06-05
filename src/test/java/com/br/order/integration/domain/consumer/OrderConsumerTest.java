package com.br.order.integration.domain.consumer;

import com.br.order.domain.consumer.OrderConsumer;
import com.br.order.domain.enums.Status;
import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.order.service.OrderService;
import com.br.order.domain.product.ProductResponse;
import com.br.order.OrderApplication;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@DirtiesContext(classMode = BEFORE_CLASS)
@ContextConfiguration(classes = OrderApplication.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integtest")
public class OrderConsumerTest {
    @Autowired
    private OrderConsumer orderConsumer;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldConsumeOrderSuccessfully() {
        // given
        UUID orderId = UUID.randomUUID();

        OrderResponse incomingOrder = OrderResponse.builder()
                .id(orderId)
                .products(List.of(
                        ProductResponse.builder()
                                .id(UUID.randomUUID())
                                .name("Café")
                                .price(BigDecimal.valueOf(10))
                                .quantity(2)
                                .build()
                ))
                .build();

        OrderResponse calculatedOrder = OrderResponse.builder()
                .id(orderId)
                .products(incomingOrder.getProducts())
                .total(BigDecimal.valueOf(20))
                .build();

        when(orderService.existsOrderById(orderId)).thenReturn(false);
        when(orderService.calculateValueOfProducts(incomingOrder)).thenReturn(calculatedOrder);

        // when
        orderConsumer.consume(incomingOrder);

        // then
        ArgumentCaptor<OrderResponse> captor = ArgumentCaptor.forClass(OrderResponse.class);
        verify(orderService).save(captor.capture());
        verify(orderService).receiveOrder(any(OrderResponse.class));

        OrderResponse saved = captor.getValue();

        assertThat(saved).isNotNull();
        assertThat(saved.getTotal()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(saved.getStatus()).isEqualTo(Status.COMPLETED);
    }
}
