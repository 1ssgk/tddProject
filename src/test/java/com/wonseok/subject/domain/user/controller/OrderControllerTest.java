package com.wonseok.subject.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonseok.subject.domain.user.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    public OrderServiceImpl orderService;

    @BeforeEach
    void init() {
    }

    @DisplayName("주문 생성")
    @WithMockUser(username = "dnjstjr12", authorities = {"ADMIN"})
    @Test
    void createOrder() throws Exception {
        //given
        List requestData = new ArrayList();
        Map<String,Object> order1 = new HashMap<>();
        order1.put("itemId", 1L);
        order1.put("count", 3);

        Map<String,Object> order2 = new HashMap<>();
        order2.put("itemId", 2L);
        order2.put("count", 4);

        requestData.add(order1);
        requestData.add(order2);

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/ws/order")
                                .characterEncoding("UTF-8")
                                .with(csrf())
                                .with(testSecurityContext())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestData))
                )
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions
                .andExpect(status().isOk());

    }

    @DisplayName("주문 취소")
    @WithMockUser(username = "dnjstjr12", authorities = {"ADMIN"})
    @Test
    void cancel() throws Exception {
        //given
        int orderId = 1;

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/ws/order/"+orderId+"/cancel")
                                .characterEncoding("UTF-8")
                                .with(csrf())
                                .with(testSecurityContext())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions
                .andExpect(status().isOk());
    }

    @DisplayName("주문 삭제")
    @WithMockUser(username = "dnjstjr12", authorities = {"ADMIN"})
    @Test
    void delete() throws Exception {
        //given
        int orderId = 1;

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/ws/order/" + orderId)
                                .characterEncoding("UTF-8")
                                .with(csrf())
                                .with(testSecurityContext())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions
                .andExpect(status().isOk());
    }
}