package com.wonseok.subject.domain.manager.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.manager.dto.item.ItemDto;
import com.wonseok.subject.domain.manager.service.impl.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ADMIN 권한이 있는 유저가 사용하는
 * Item 관리 Controller
 */

@RestController
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ItemController {
    private final ItemServiceImpl itemService;

    @PostMapping("/ws/item")
    public ResponseEntity save(@RequestBody ItemDto itemDto) {
        itemService.create(itemDto);
        return SuccessResponse.ok();
    }

    @DeleteMapping("/ws/item/{itemId}/delete")
    public ResponseEntity delete(@PathVariable Long itemId) {
        itemService.delete(itemId);
        return SuccessResponse.ok();
    }

    @PutMapping("/ws/item/{itemId}/edit")
    public ResponseEntity edit(@PathVariable Long itemId, ItemDto itemDto) {

        itemService.update(itemId, itemDto);
        return SuccessResponse.ok();
    }
}
