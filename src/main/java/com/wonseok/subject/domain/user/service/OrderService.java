package com.wonseok.subject.domain.user.service;

import com.wonseok.subject.domain.user.dto.order.OrderDto;

import java.util.List;

public interface OrderService {
    public void order(String userId, List<OrderDto> order);

    public void cancel(String userId, Long orderId);

    public void delete(String userId, Long orderId);
}
