package com.wonseok.subject;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonseok.subject.domain.controller.SignController;
import com.wonseok.subject.domain.service.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

//@SpringBootTest
//@AutoConfigureMockMvc

@WebMvcTest(SignController.class)
public class SignControllerTest {


    @BeforeEach
    public void initData() {
        Map<String, String> input = new HashMap<>();
        input.put("userId", "hong12");
        input.put("password", "123456");
        input.put("userNm", "홍길동");
        input.put("regNo", "860824-1655068");
    }

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public SignService signService;

//    @Nested
//    class Signup {
//        @DisplayName("API POST 회원가입 성공 (200)")  /* 성공 */
//        @Test
//        void success() throws Exception {
//            //given
//            Map<String,Object> params = new HashMap<>();
//            params.put("userId", "hong12");
//            params.put("password", "123456");
//            params.put("userNm", "홍길동");
//            params.put("regNo", "860824-1655068");
//
//            Map<String,Object> result = new HashMap<>();
//            result.put("success",true);
//
//
//            given(signService.signup(any()))
//                    .willReturn();
//
//            ResultActions resultActions = mockMvc.perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/signup")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(map)))
//                    .andDo(MockMvcResultHandlers.print());
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andReturn();
//        }

//        @DisplayName("API POST 회원가입 에러 (400)")  /* 필수값 에러 */
//        @Test
//        void badRequest() throws Exception {
//            Map<String, String> map = initData();
//            map.put("regNo", null);
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/signup")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(map)))
//                    .andDo(MockMvcResultHandlers.print());
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andReturn();
//        }
//
//        @DisplayName("회원가입 에러 (404)")  /* 회원가입을 할 수 없는 유저인 경우 */
//        @Test
//        void notFound() throws Exception {
//            Map<String, String> map = initData();
//            map.put("regNo", "860824-1655061");
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/signup")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(map)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//        @DisplayName("회원가입 에러 (405)")  /* 요청 변경 POST -> GET */
//        @Test
//        void methodNotAllowed() throws Exception {
//            Map<String, String> map = initData();
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .get("/ws/signup")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(map)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//        @DisplayName("API POST 회원가입 에러 (409)")  /* 계정 중복 */
//        @Test
//        void conflict() throws Exception {
//            success();
//
//            Map<String, String> map = initData();
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/signup")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(map)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//    }
//
//
//    @Nested
//    class Login {
//        Signup signup = new Signup();
//
//        @DisplayName("로그인 성공 (200)")   /* 성공 */
//        @Test
//        void success() throws Exception {
//            signup.success();
//
//            Map<String, String> input = new HashMap<>();
//            input.put("userId", "hong12");
//            input.put("password", "123456");
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/login")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(input)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().isOk())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//        @DisplayName("로그인 에러 (400)")    /* 필수값 에러 */
//        @Test
//        void badRequest() throws Exception {
//            signup.success();
//
//            Map<String, String> input = new HashMap<>();
//            input.put("userId", "hong12");
//            input.put("password", null);
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/login")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(input)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//        @DisplayName("API POST 로그인 에러 (404)")   /* 계정이 없는 경우 */
//        @Test
//        void notFound() throws Exception {
//            //signup.success();  // <- 회원가입을 안함
//
//            Map<String, String> input = new HashMap<>();
//            input.put("userId", "hong12");
//            input.put("password", "123456");
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .post("/ws/login")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(input)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//        @DisplayName("API POST 로그인 에러 (405)")  /* 요청 변경 POST -> GET */
//        @Test
//        void methodNotAllowed() throws Exception {
//            //signup.success();
//
//            Map<String, String> input = new HashMap<>();
//            input.put("userId", "hong12");
//            input.put("password", "123456");
//
//            ResultActions resultActions = mockMvc
//                    .perform(
//                            MockMvcRequestBuilders
//                                    .get("/ws/login")
//                                    .characterEncoding("UTF-8")
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(objectMapper.writeValueAsString(input)));
//
//            resultActions
//                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                    .andDo(MockMvcResultHandlers.print())
//                    .andReturn();
//        }
//
//
//
//    }


}
