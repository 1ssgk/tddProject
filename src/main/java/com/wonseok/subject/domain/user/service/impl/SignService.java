package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.common.encryption.Aes256;
import com.wonseok.subject.domain.common.exception.ConflictException;
import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.user.dto.SigninDto;
import com.wonseok.subject.domain.user.dto.SignupDto;
import com.wonseok.subject.domain.user.entity.Authority;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.repository.SignAbleUserRepository;
import com.wonseok.subject.domain.user.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wonseok.subject.domain.common.encryption.Sha512;
import com.wonseok.subject.domain.common.token.TokenProvider;
import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.common.exception.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Collections;


@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final SignAbleUserRepository signAbleUserRepository;
    private final Sha512 sha512;
    private final Aes256 aes256;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseEntity<SuccessResponse> signup(SignupDto signupDto) {
        try {
            if (memberRepository.existsByUserId(signupDto.getUserId())) {
                throw new ConflictException("사용할 수 없는 아이디 입니다.");
            }
            if (!signAbleUserRepository.existsSignAbleUsersByRegNoAndUserNm(aes256.encrypt(signupDto.getRegNo()), signupDto.getUserNm())) {
                throw new NotFoundException("서비스 이용이 불가능합니다.");
            }

            String encPassword = sha512.encrypt(signupDto.getPassword());

            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            Member saveMember = Member.builder()
                    .userId(signupDto.getUserId())
                    .password(encPassword)
                    .memberNm(signupDto.getUserNm())
                    .authorities(Collections.singleton(authority))
                    .regNo(signupDto.getRegNo())
                    .build();

            // 회원 등록
            Member resultMember = memberRepository.save(saveMember);

        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new NoSuchAlgorithmException("Internal Server Error");
        }

        return SuccessResponse.ok();
    }

    public ResponseEntity<SuccessResponse> login(SigninDto signinDto, HttpServletResponse response) {
        String jwtToken = null;
        try {

            Member findMember = memberRepository
                    .findByUserId(signinDto.getUserId())
                    .orElseThrow(()
                            -> {
                        throw new NotFoundException("계정이 존재하지 않습니다.");
                    });

            String encPassword = sha512.encrypt(signinDto.getPassword());

            boolean isEqualPassword = findMember.getPassword().equals(encPassword);

            if (!isEqualPassword) {
                throw new NotFoundException("계정 또는 비밀번호가 다릅니다.");
            }

            jwtToken = tokenProvider.makeJwtToken(findMember);
            tokenProvider.setToken(jwtToken, response);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new NoSuchAlgorithmException("Internal Server Error");
        }

        return SuccessResponse.ok();
    }
}
