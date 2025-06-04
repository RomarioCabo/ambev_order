package com.br.order.domain.order;

import com.br.order.domain.enums.Status;
import com.br.order.domain.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    @JsonProperty("order_id")
    private UUID id;
    private BigDecimal total;
    private Status status;
    private List<ProductResponse> products;
}
