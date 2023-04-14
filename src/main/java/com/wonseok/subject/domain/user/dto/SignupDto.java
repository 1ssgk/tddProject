package com.wonseok.subject.domain.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
@Schema(name = "SignupData")
public class SignupDto {

    @Schema(example = "hong12")
    @NotNull
    private String userId;

    @Schema(example = "123456")
    @NotNull
    private String password;

    @Schema(example = "홍길동")
    @NotNull
    private String userNm;

    @Schema(example = "860824-1655068")
    @NotNull
    private String regNo;
}
