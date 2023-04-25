package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.user.entity.Member;
import com.wonseok.subject.domain.user.entity.OrderItem;
import com.wonseok.subject.domain.user.entity.TestTa;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.repository.OrderItemRepository;
import com.wonseok.subject.domain.user.repository.TestTaRepository;
import com.wonseok.subject.domain.user.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public void edit(String userId, Long orderItemId) {


    }

}
