package com.br.order.infrastructure.persistence.order;

import com.br.order.domain.enums.Status;
import com.br.order.domain.order.OrderResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class OrderEntity {

    @Id
    @Column(name = "id_pedido", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public OrderEntity(OrderResponse order) {
        this.id = order.getId();
        this.total = order.getTotal();
        this.status = order.getStatus();
        this.createdAt = LocalDateTime.now();
    }
}
