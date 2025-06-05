package com.br.order.infrastructure.provider;

import com.br.order.domain.order.OrderResponse;
import com.br.order.domain.provider.OrderProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProviderImpl implements OrderProvider {
    private final OrderHttpRestClient restClient;

    @Override
    public void receiveOrder(OrderResponse request) {
        log.info("trying to send request to produto_externo_b: {}", request);
        restClient.receiveOrder(request);
    }
}
