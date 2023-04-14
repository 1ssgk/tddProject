package com.wonseok.subject.domain.user.repository;

import com.wonseok.subject.domain.user.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
