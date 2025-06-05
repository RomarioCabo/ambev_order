package com.br.order.infrastructure.provider;

import com.br.order.domain.order.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "produto-externo-b", url = "http://localhost:8092")
public interface OrderHttpRestClient {

    @PostMapping("/api/v1/pedido")
    void receiveOrder(@RequestBody OrderResponse request);
}
