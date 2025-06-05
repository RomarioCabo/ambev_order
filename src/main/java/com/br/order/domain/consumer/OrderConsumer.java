package com.br.order.domain.consumer;

import com.br.order.domain.enums.Status;
import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(OrderResponse order) {
        try {
            log.info("Consuming order: {}", order);

            if (orderService.existsOrderById(order.getId())) {
                log.info("Request discarded because it exists in the database: {}", order);
                return;
            }

            OrderResponse orderResponse = orderService.calculateValueOfProducts(order);

            if (orderResponse == null) {
                log.info("Request discarded because order is null or order.products is null: {}", order);
                return;
            }

            orderResponse.setStatus(Status.COMPLETED);

            log.info("Order before calculate: {}", orderResponse);

            orderService.save(orderResponse);

            orderService.receiveOrder(orderResponse);
        } catch (Exception e) {
            log.info("Error to consume: {}", e.getMessage());
        }
    }
}
