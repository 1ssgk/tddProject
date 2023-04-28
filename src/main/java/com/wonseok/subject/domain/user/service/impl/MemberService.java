package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.user.dto.MemberDto;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseEntity<SuccessResponse> getMyMemberData(String memberId) {
        return SuccessResponse.result(
                HttpStatus.OK,
                MemberDto.of(
                        memberRepository.findOneMemberByMemberId(memberId)
                                .orElseThrow(() -> {
                                    throw new NotFoundException("데이터가 존재하지 않습니다.");
                                }))
        );
    }
}
