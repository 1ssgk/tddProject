package com.wonseok.subject.domain.user.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.wonseok.subject.domain.user.dto.SigninDto;
import com.wonseok.subject.domain.user.dto.SignupDto;
import com.wonseok.subject.domain.user.service.impl.SignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonseok.subject.domain.common.dto.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class SignController {
    private final SignService signService;

    public SignController(SignService signService) {
        this.signService = signService;
    }

    @Operation(summary = "회원 가입 요청", description = "회원가입을 합니다.", tags = {"Sign"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "405", description = "METHOD_NOT_ALLOWED"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")
    })

    @PostMapping("/ws/signup")
    public ResponseEntity<SuccessResponse> signup(@RequestBody @Valid SignupDto signupDto) {
        return signService.signup(signupDto);
    }

    @Operation(summary = "회원 로그인", description = "로그인을 합니다.", tags = {"Sign"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
            @ApiResponse(responseCode = "405", description = "METHOD_NOT_ALLOWED")
    })
    @PostMapping("/ws/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody @Valid SigninDto signinDto, HttpServletResponse response) {
        //return ResponseEntity.status(200).body(null);
        return signService.login(signinDto, response);
    }
}
