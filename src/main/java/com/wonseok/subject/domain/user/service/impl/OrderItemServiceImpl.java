package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.user.repository.OrderItemRepository;
import com.wonseok.subject.domain.user.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public void edit(String userId, Long orderItemId) {


    }

}
