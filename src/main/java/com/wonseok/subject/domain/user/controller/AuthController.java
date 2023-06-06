package com.wonseok.subject.domain.user.controller;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @GetMapping("/ws/authenticate")
    public ResponseEntity<SuccessResponse> authCheck() {

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
