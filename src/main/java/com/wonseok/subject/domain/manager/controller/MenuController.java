package com.wonseok.subject.domain.manager.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.manager.dto.menu.MenuDto;
import com.wonseok.subject.domain.manager.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    //생성
    @PostMapping("/menu/create")
    public ResponseEntity<SuccessResponse> create(@RequestBody List<MenuDto> createMenuList) {
        menuService.create(createMenuList);
        return SuccessResponse.result(HttpStatus.CREATED);
    }

    //수정
    @PostMapping("/menu/update")
    public ResponseEntity<SuccessResponse> update(@RequestBody List<MenuDto> updateMenuList) {
        menuService.update(updateMenuList);
        return SuccessResponse.result(HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/menu/delete")
    public ResponseEntity<SuccessResponse> delete(@RequestBody MenuDto deleteMenu) {
        menuService.delete(deleteMenu);
        return SuccessResponse.result(HttpStatus.OK);
    }

    //조회
    // 메뉴가 그렇게 많지 않을거라 생각해서 우선 전체 조회만
    @GetMapping("/menu")
    public ResponseEntity<SuccessResponse> find() {
        return SuccessResponse.result(menuService.findAll());
    }
}
