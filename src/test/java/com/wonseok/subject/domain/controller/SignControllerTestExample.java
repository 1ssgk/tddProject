//package com.wonseok.subject.domain.controller;
//
//
//import io.restassured.RestAssured;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static io.restassured.RestAssured.*;
//import static org.assertj.core.api.Assertions.*;
//
///**
// * @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
// * - 해당 테스트 종료 후에 컨텍스틑 폐기하고 새로운 애플리케이션 컨텍스트를 생성
// * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// * - 실행 시 랜덤한 포트로 실행
// */
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class SignControllerTestExample {
//
//    @LocalServerPort
//    int port;
//
//    @BeforeEach
//    public void setUp() {
//        RestAssured.port = port;
//    }
//
//    @DisplayName("회원 가입 성공")
//    @Test
//    void signUp() {
//        //given
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "hong12");
//        params.put("password", "123456");
//        params.put("userNm", "홍길동");
//        params.put("regNo", "860824-1655068");
//
//        //when
//        ExtractableResponse<Response> response = given()
//                .body(params)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .post("/ws/signup")
//                .then().log().all()
//                .extract();
//
//        int statusCode = response.statusCode();
//        String status = response.jsonPath().get("status");
//
//        //then
//        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
//        assertThat(status).isEqualTo("success");
//    }
//
//    @DisplayName("회원 가입 실패")
//    @Test
//    void signUp_fail() {
//        //given
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "hong12");
//        params.put("password", "123456");
//        params.put("userNm", "홍길동");
//        params.put("regNo", "860824-1655069");
//
//        //when
//        ExtractableResponse<Response> response = given()
//                .body(params)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .post("/ws/signup")
//                .then().log().all()
//                .extract();
//
//        int statusCode = response.statusCode();
//        boolean success = response.jsonPath().get("success");
//        Map<String, Object> errors = response.jsonPath().get("errors");
//
//        //then
//        assertThat(statusCode).isEqualTo(HttpStatus.NOT_FOUND.value());
//        assertThat(success).isEqualTo(false);
//        assertThat(errors.get("code")).isEqualTo(404);
//    }
//
//}