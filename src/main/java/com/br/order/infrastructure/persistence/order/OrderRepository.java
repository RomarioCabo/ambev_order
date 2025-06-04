package com.br.order.infrastructure.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("SELECT COUNT(o) > 0 FROM OrderEntity o WHERE o.id = :id")
    boolean existsById(@Param("id") UUID id);
}
