package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.manager.repository.ItemRepository;
import com.wonseok.subject.domain.user.dto.order.OrderDto;
import com.wonseok.subject.domain.user.entity.Address;
import com.wonseok.subject.domain.user.entity.Authority;
import com.wonseok.subject.domain.user.entity.Delivery;
import com.wonseok.subject.domain.user.entity.DeliveryStatus;
import com.wonseok.subject.domain.user.entity.Item;
import com.wonseok.subject.domain.user.entity.Member;
import com.wonseok.subject.domain.user.entity.Order;
import com.wonseok.subject.domain.user.entity.OrderItem;
import com.wonseok.subject.domain.user.entity.OrderStatus;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ItemRepository itemRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    Member member;
    Authority authority;
    Address address;

    Item item;

    Delivery delivery;

    @BeforeEach
    void init() {
        // 계정 관련 정의
        authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        address = Address.builder()
                .city("시티")
                .street("거리")
                .zipcode("집")
                .build();

        member = Member.builder()
                .memberId(1L)
                .memberNm("운영자")
                .authorities(Collections.singleton(authority))
                .userId("dnjstjr12")
                .password("dnjstjr12")
                .regNo("9102021223333")
                .address(address)
                .build();

        item = Item.builder()
                .id(1L)
                .name("아이템1")
                .price(30000)
                .stockQuantity(10)
                .build();

        delivery = Delivery.builder()
                .id(1L)
                .deliveryStatus(DeliveryStatus.READY)
                .address(address)
                .build();
    }

    @DisplayName("주문작성 서비스")
    @WithMockUser(username = "dnjstjr12", authorities = {"ADMIN"})
    @Test
    void order() {
        //given
        String userId = "dnjstjr12";

        // 주문
        List<OrderDto> orders = new ArrayList<>();
        OrderDto order1 = OrderDto.builder()
                .itemId(1L)
                .count(3)
                .build();
        orders.add(order1);

        // 계정이 존재한다.
        given(memberRepository.findByUserId(Mockito.anyString()))
                .willReturn(Optional.of(member));

        // 아이템이 존재한다.
        given(itemRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(item));


        //when
        orderService.order(userId, orders);
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        //then
        verify(orderRepository, times(1))
                .save(captor.capture());

        assertThat(captor.getValue().getMember().getUserId())
                .isEqualTo(member.getUserId());

    }

    @DisplayName("주문취소 서비스")
    @WithMockUser(username = "dnjstjr12", authorities = {"ADMIN"})
    @Test
    void cancel() {
        //==given
        String userId = "dnjstjr12";
        Long orderId = 1L;

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .item(item)
                .orderPrice(item.getPrice())
                .count(2)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Order order = Order.builder()
                .id(orderId)
                .orderStatus(OrderStatus.ORDER)
                .orderItems(orderItems)
                .delivery(delivery)
                .member(member)
                .build();

        // 주문 신청
        orderRepository.save(order);
        System.out.println("Order status 전 : "+order.getOrderStatus());


        given(orderRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(order));

        //==when
        orderService.cancel(userId,orderId);
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        //==then
        verify(orderRepository, times(1))
                .save(captor.capture());

        // 주문 취소가 되었을 것이다 -> 확인
        assertThat(captor.getValue().getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        System.out.println("Order status 후 : "+captor.getValue().getOrderStatus());
    }

    @DisplayName("주문 삭제 서비스")
    @WithMockUser(username = "dnjstjr12",authorities = {"ADMIN"})
    @Test
    void delete() {
        //==given
        String userId = "dnjstjr12";
        Long orderId = 1L;

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .item(item)
                .orderPrice(item.getPrice())
                .count(2)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Order order = Order.builder()
                .id(orderId)
                .orderStatus(OrderStatus.CANCEL)
                .orderItems(orderItems)
                .delivery(delivery)
                .member(member)
                .build();

        orderRepository.save(order);

        given(orderRepository.findById(Mockito.anyLong()))
                .willReturn(Optional.of(order));

        //==when
        orderService.delete(userId, orderId);

        //==then

    }

}