package com.wonseok.subject.domain.user.repository;

import com.wonseok.subject.domain.user.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
