package com.wonseok.subject.domain.controller;

import com.wonseok.subject.domain.common.dto.ErrorResponse;
import com.wonseok.subject.domain.dto.MemberDto;
import com.wonseok.subject.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "사용자 데이터 요청", description = "사용자 데이터를 요청합니다.", tags = {"Member"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "409", description = "CONFLICT"),
            @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/ws/me")
    @Parameter(name="Authorization",in = ParameterIn.HEADER, example = "Bearer {JwtToken}", required = true)
    public ResponseEntity getMyMemberData(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return memberService.getMyMemberData(userDetails.getUsername());
    }
}
