package com.wonseok.subject.domain.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;

@Getter
@AllArgsConstructor
public class ErrorResponse<T> {

    @Schema(defaultValue = "false")
    private String status;

    private T data;
    private T errors;

    public static <T> ErrorResponse<T> success(T data) {
        return new ErrorResponse<>("error", data, null);
    }

    public static <T> ErrorResponse<T> successHeader(T data, HttpHeaders headers) {
        return new ErrorResponse<>("error", data, null);
    }

    public static <T> ErrorResponse<T> fail(T message) {
        return new ErrorResponse<>("error", null, message);
    }
}
