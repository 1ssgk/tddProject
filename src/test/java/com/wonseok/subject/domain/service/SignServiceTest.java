package com.wonseok.subject.domain.service;

import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.common.encryption.Aes256;
import com.wonseok.subject.domain.common.encryption.Sha512;
import com.wonseok.subject.domain.common.exception.ConflictException;
import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.common.token.TokenProvider;
import com.wonseok.subject.domain.user.dto.SigninDto;
import com.wonseok.subject.domain.user.dto.SignupDto;
import com.wonseok.subject.domain.user.entity.Authority;
import com.wonseok.subject.domain.user.entity.Member;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.repository.SignAbleUserRepository;
import com.wonseok.subject.domain.user.service.impl.SignService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class SignServiceTest {
    Authority authority = Authority.builder()
            .authorityName("ROLE_USER")
            .build();

    Member member = Member.builder()
            .memberId(1L)
            .userId("hong12")
            .memberNm("홍길동")
            .password("123456")
            .regNo("860824-1655068")
            .authorities(Collections.singleton(authority))
            .build();

    @Nested
    class SignUp {
        @Mock
        MemberRepository memberRepository;
        @Mock
        SignAbleUserRepository signAbleUserRepository;
        @Mock
        Sha512 sha512;
        @Mock
        Aes256 aes256;
        @InjectMocks
        SignService signService;


        @DisplayName("회원 가입 성공")
        @WithMockUser
        @Test
        void success() throws NoSuchAlgorithmException {
            //given
            SignupDto requestData = SignupDto.builder()
                    .userId("hong12")
                    .userNm("홍길동")
                    .password("123456")
                    .regNo("860824-1655068")
                    .build();

            // ** 중복된 계정을 확인 -> 없음
            given(memberRepository.existsByUserId(requestData.getUserId())).willReturn(false);

            // ** 회원가입이 가능한 사람인지 확인 -> 가능
            given(signAbleUserRepository
                    .existsSignAbleUsersByRegNoAndUserNm(aes256.encrypt(requestData.getRegNo()), requestData.getUserNm()))
                    .willReturn(true);

            // ** 비밀번호도 반환해줘야했나...
            given(sha512.encrypt(requestData.getPassword()))
                    .willReturn(requestData.getPassword());

            //when
            ResponseEntity<SuccessResponse> result = signService.signup(requestData);
            ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

            //then
            verify(memberRepository, times(1)).save(captor.capture());
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(result.getBody().getStatus()).isEqualTo("success");
            //assertEquals(captor.getValue().getUserId(), member.getUserId());
        }

        @DisplayName("중복됨")
        @WithMockUser
        @Test
        void fail1() {
            //given
            SignupDto requestData = SignupDto.builder()
                    .userId("hong12")
                    .userNm("홍길동")
                    .password("123456")
                    .regNo("860824-1655068")
                    .build();

            // ** 중복된 계정을 확인 -> 존재함
            given(memberRepository.existsByUserId(requestData.getUserId())).willReturn(true);

            //when,then
            assertThatThrownBy(() -> {
                // signService.signup 메소드 에서 throw 가 발생할 것이다.
                ResponseEntity<SuccessResponse> result = signService.signup(requestData);
            })
                    .isExactlyInstanceOf(ConflictException.class) // 발생 할 throw
                    .isInstanceOf(RuntimeException.class) // 부모 throw , 안적어도 됨
                    .hasMessage("사용할 수 없는 아이디 입니다.");   // 발생 시 errorMessage
        }

        @DisplayName("계정 생성 권한이 없는 사용자")
        @WithMockUser
        @Test
        void fail2() throws NoSuchAlgorithmException {
            //given
            SignupDto requestData = SignupDto.builder()
                    .userId("hong12")
                    .userNm("홍길동")
                    .password("123456")
                    .regNo("860824-1655068")
                    .build();

            // ** 중복된 계정을 확인 -> 없음
            given(memberRepository.existsByUserId(requestData.getUserId())).willReturn(false);

            // ** 회원가입이 가능한 사람인지 확인 -> 불가능
            given(signAbleUserRepository
                    .existsSignAbleUsersByRegNoAndUserNm(sha512.encrypt(requestData.getRegNo()), requestData.getUserNm()))
                    .willReturn(false);

            //when,then
            assertThatThrownBy(() -> {
                // signService.signup 메소드 에서 throw 가 발생할 것이다.
                ResponseEntity<SuccessResponse> result = signService.signup(requestData);
            })
                    .isExactlyInstanceOf(NotFoundException.class) // 발생할 throw
                    .isInstanceOf(RuntimeException.class) // 부모 throw , 안적어도 됨
                    .hasMessage("서비스 이용이 불가능합니다.");   // 발생 시 errorMessage
        }

    }

    @Nested
    class Login {
        @Mock
        MemberRepository memberRepository;
        @Mock
        SignAbleUserRepository signAbleUserRepository;
        @Mock
        Sha512 sha512;
        @Mock
        Aes256 aes256;
        @Mock
        Member member;
        @Mock
        TokenProvider tokenProvider;
        @InjectMocks
        SignService signService;

        @DisplayName("로그인 성공")
        @Test
        @WithMockUser
        void success() throws NoSuchAlgorithmException {
            //given
            SigninDto requestData = SigninDto.builder()
                    .userId("hong12")
                    .password("123456")
                    .build();

            String encPassword = "123456";

            //** 유저 데이터가 존재하는가 -> 있다.
            given(memberRepository.findByUserId(requestData.getUserId()))
                    .willReturn(Optional.of(member));

            //** 암호화된 비밀번호를 설정
            given(sha512.encrypt(requestData.getPassword()))
                    .willReturn(encPassword);

            //** 토큰이 발행될 것이다 명시
            given(tokenProvider.makeJwtToken(member))
                    .willReturn("jwtToken");

            //** member.getPassword() 메소드 실행 결과 -> "123456" 임의값 반환 설정
            // private,final,equals... 등등 의 실핼 결과에 대한 값은 지정을 해줘야 한다.
            doReturn("123456")
                    .when(member)
                    .getPassword();

            //when
            ResponseEntity<SuccessResponse> result = signService.login(requestData, any());

            //then
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @DisplayName("계정 없음")
        @Test
        @WithMockUser
        void fail1() {
            //given
            SigninDto requestData = SigninDto.builder()
                    .userId("hong12")
                    .password("123456")
                    .build();

            //** 유저 데이터가 존재하는가 -> 없다.
            given(memberRepository.findByUserId(requestData.getUserId()))
                    .willReturn(Optional.empty());

            //when, then
            assertThatThrownBy(() -> {
                // signService.login 메소드 에서 throw 가 발생할 것이다.
                ResponseEntity<SuccessResponse> result = signService.login(requestData, any());
            })
                    .isExactlyInstanceOf(NotFoundException.class) // 발생할 throw
                    .isInstanceOf(RuntimeException.class) // 부모 throw , 안적어도 됨
                    .hasMessage("계정이 존재하지 않습니다.");   // 발생 시 errorMessage
        }

        @DisplayName("비밀번호 틀림")
        @Test
        @WithMockUser
        void fail2() throws NoSuchAlgorithmException {
            //given
            SigninDto requestData = SigninDto.builder()
                    .userId("hong12")
                    .password("123456")
                    .build();

            String encPassword = "123456";

            //** 유저 데이터가 존재하는가 -> 있다.
            given(memberRepository.findByUserId(requestData.getUserId()))
                    .willReturn(Optional.of(member));

            //** 암호화된 비밀번호를 설정
            given(sha512.encrypt(requestData.getPassword()))
                    .willReturn(encPassword);

            //** member.getPassword() 메소드 실행 결과 -> "123456" 임의값 반환 설정
            // private,final,equals... 등등 의 실핼 결과에 대한 값은 지정을 해줘야 한다.
            doReturn("123457")  // 뒷자리 다르게 수정
                    .when(member)
                    .getPassword();

            //when, then
            assertThatThrownBy(() -> {
                // signService.login 메소드 에서 throw 가 발생할 것이다.
                ResponseEntity<SuccessResponse> result = signService.login(requestData, any());
            })
                    .isExactlyInstanceOf(NotFoundException.class) // 발생할 throw
                    .isInstanceOf(RuntimeException.class) // 부모 throw , 안적어도 됨
                    .hasMessage("계정 또는 비밀번호가 다릅니다.");   // 발생 시 errorMessage
        }

    }

}