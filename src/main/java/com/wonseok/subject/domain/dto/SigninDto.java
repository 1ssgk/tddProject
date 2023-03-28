package com.wonseok.subject.domain.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
@Schema(name = "SigninData")
public class SigninDto {
    @Schema(example = "hong12")
    @NotNull
    private String userId;

    @Schema(example = "123456")
    @NotNull
    private String password;

}
