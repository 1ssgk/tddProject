package com.wonseok.subject.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonseok.subject.domain.user.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
@Schema(name = "MemberData")
public class MemberDto {

    @Schema(example = "hong12")
    @JsonProperty(value = "아이디")
    private String userId;

    @Schema(example = "홍길동")
    @JsonProperty(value = "이름")
    private String memberNm;

    @Schema(example = "2023-02-17T20:44:24.551475")
    @JsonProperty(value = "가입날짜")
    private LocalDateTime createDt;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .memberNm(member.getMemberNm())
                .build();
    }
}
