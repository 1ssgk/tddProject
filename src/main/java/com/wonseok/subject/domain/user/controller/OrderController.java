package com.wonseok.subject.domain.user.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.user.dto.order.OrderDto;
import com.wonseok.subject.domain.user.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/ws/order")
    public ResponseEntity<SuccessResponse> createOrder(@RequestBody List<OrderDto> requestOrder, Principal principal) {
        String userId = principal.getName();
        orderService.order(userId, requestOrder);
        return SuccessResponse.result(HttpStatus.CREATED);
    }

    @PutMapping("/ws/order/{orderId}/cancel")
    public ResponseEntity<SuccessResponse> cancel(@PathVariable Long orderId, Principal principal) {
        String userId = principal.getName();
        orderService.cancel(userId, orderId);
        return SuccessResponse.result(HttpStatus.OK);
    }

    @DeleteMapping("/ws/order/{orderId}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long orderId, Principal principal) {
        String userId = principal.getName();
        orderService.delete(userId, orderId);
        return SuccessResponse.result(HttpStatus.OK);
    }
}
