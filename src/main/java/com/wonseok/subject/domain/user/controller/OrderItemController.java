package com.wonseok.subject.domain.user.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.user.dto.order.OrderItemDto;
import com.wonseok.subject.domain.user.entity.Member;
import com.wonseok.subject.domain.user.entity.TestTa;
import com.wonseok.subject.domain.user.service.impl.OrderItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemServiceImpl orderItemService;

    @PostMapping("/ws/orderItem/{orderItemId}/edit")
    public ResponseEntity edit(@PathVariable Long orderItemId, Principal principal) {
        String userId = principal.getName();
        orderItemService.edit(userId, orderItemId);

        return SuccessResponse.ok();
    }
}
