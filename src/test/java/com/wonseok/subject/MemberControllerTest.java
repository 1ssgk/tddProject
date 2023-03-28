package com.wonseok.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonseok.subject.domain.common.token.TokenProvider;
import com.wonseok.subject.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenProvider tokenProvider;

    @Value("${jwt.header}") String header;
    @Value("${jwt.secret}") String secret;
    @Value("${jwt.authorities-key}") String authoritiesKey;
    @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds;

    @Autowired
    private ObjectMapper objectMapper;

    private Collection<? extends GrantedAuthority> authoriteis(Member member){
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
        return grantedAuthorities;
    }

    private Collection<? extends GrantedAuthority> getAutoriteis(){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return list;
    }

    @DisplayName("API POST 회원가입")
    @Test
    @WithMockUser
    void signup() throws Exception {

        Map<String,String> input = new HashMap<>();

        input.put("userId", "hong12");
        input.put("password", "123456");
        input.put("userNm", "홍길동");
        input.put("regNo", "860824-1655068");

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/ws/signup")
                                .characterEncoding("UTF-8")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(input)))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @DisplayName("API GET ME")
    @Test
    void testMe() throws Exception {
        signup();

        String JwtToken = tokenProvider.makeJwtToken(Member.builder()
                        .userId("hong12")
                        .memberNm("홍길동")
                        .password("123456")
                        .regNo("860824-1655068")
                        .build());


        ResultActions resultActions = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/ws/me")
                                .header("Authorization","Bearer "+JwtToken)
                                //.with(csrf())
                                .characterEncoding("UTF-8")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print());


        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
