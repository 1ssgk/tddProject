package com.wonseok.subject.domain.user.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.manager.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/home/getMenu")
    public ResponseEntity<SuccessResponse> getMenu() {
        return SuccessResponse.result(menuService.findAll());
    }


}
