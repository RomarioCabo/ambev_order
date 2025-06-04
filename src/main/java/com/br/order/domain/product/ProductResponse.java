package com.br.order.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @JsonProperty("product_id")
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
}
