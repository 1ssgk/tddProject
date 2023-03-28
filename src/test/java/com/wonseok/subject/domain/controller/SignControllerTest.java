package com.wonseok.subject.domain.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonseok.subject.domain.common.dto.SuccessResponse;
import com.wonseok.subject.domain.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *  SignController api 테스트
 */
@WebMvcTest(SignController.class)
public class SignControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public SignService signService;

    @Nested
    class SignUp {

        @DisplayName("회원 가입 성공")
        @WithMockUser // spring security 로 인해서 설정
        @Test
        void success() throws Exception {
            //given
            Map<String, Object> params = new HashMap<>();
            params.put("userId", "hong12");
            params.put("password", "123456");
            params.put("userNm", "홍길동");
            params.put("regNo", "860824-1655068");

            ResponseEntity<SuccessResponse> result = SuccessResponse.ok();

            //** 회원가입 시도 -> 결과값 반환(성공)
            given(signService.signup(any()))
                    .willReturn(result);

            //when
            ResultActions resultActions = mockMvc.perform(
                            MockMvcRequestBuilders
                                    .post("/ws/signup")
                                    .characterEncoding("UTF-8")
                                    .with(csrf())
                                    .with(testSecurityContext())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(params)))
                    .andDo(MockMvcResultHandlers.print());

            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"));

        }

        @DisplayName("회원가입 실패")
        @WithMockUser
        @Test
        void fail() throws Exception {
            //given
            Map<String, Object> params = new HashMap<>();
            //params.put("userId", "hong12");  // 일부러 주석해놓음
            params.put("password", "123456");
            params.put("userNm", "홍길동");
            params.put("regNo", "860824-1655068");

            //when
            ResultActions resultActions = mockMvc.perform(
                            MockMvcRequestBuilders
                                    .post("/ws/signup")
                                    .characterEncoding("UTF-8")
                                    .with(csrf())
                                    .with(testSecurityContext())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(params)))
                    .andDo(MockMvcResultHandlers.print());

            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.status").value("error"));
        }
    }

    @Nested
    class Login {

        @Test
        @DisplayName("로그인 성공")
        @WithMockUser
        void success() throws Exception {
            //given
            Map<String,Object> params = new HashMap<>();
            params.put("userId","hong12");
            params.put("password","123456");

            //** 로그인 시도 -> 결과 값 반환(성공)
            given(signService.login(any(),any()))
                    .willReturn(SuccessResponse.ok());

            //when
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/ws/login")
                            .with(csrf())
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(params))
            ).andDo(MockMvcResultHandlers.print());

            //then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"));
        }

        @Test
        @DisplayName("로그인 실패")
        @WithMockUser
        void fail() throws Exception {
            //given
            Map<String,Object> params = new HashMap<>();
            params.put("userId","hong12");
//            params.put("password","123456");  //일부러 주석    error 발생

            //when
            ResultActions resultActions = mockMvc.perform(
                    MockMvcRequestBuilders
                            .post("/ws/login")
                            .with(csrf())
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(params))
            ).andDo(MockMvcResultHandlers.print());

            //then
            resultActions
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.status").value("error"));
        }
    }


}
