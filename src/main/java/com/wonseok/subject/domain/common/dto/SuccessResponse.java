package com.wonseok.subject.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse {
    private String status;
    private Object data;

    public static ResponseEntity<SuccessResponse> result(Object data) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        SuccessResponse.builder()
                        .status("success")
                        .data(data)
                        .build()
                );
    }

    public static ResponseEntity<SuccessResponse> result(HttpStatus code) {
        return ResponseEntity
                .status(code)
                .body(
                        SuccessResponse.builder()
                        .status("success")
                        .build()
                );
    }
}
