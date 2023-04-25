package com.wonseok.subject.domain.user.service.impl;

import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.manager.repository.ItemRepository;
import com.wonseok.subject.domain.user.dto.order.OrderDto;
import com.wonseok.subject.domain.user.entity.Delivery;
import com.wonseok.subject.domain.user.entity.DeliveryStatus;
import com.wonseok.subject.domain.user.entity.Item;
import com.wonseok.subject.domain.user.entity.Member;
import com.wonseok.subject.domain.user.entity.Order;
import com.wonseok.subject.domain.user.entity.OrderItem;
import com.wonseok.subject.domain.user.entity.OrderStatus;
import com.wonseok.subject.domain.user.repository.MemberRepository;
import com.wonseok.subject.domain.user.repository.OrderRepository;
import com.wonseok.subject.domain.user.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    private final OrderRepository orderRepository;

    @Override
    public void order(String userId, List<OrderDto> requestOrder) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    // 에러 발생 : 존재하지 않는 계정입니다.
                    throw new NotFoundException();
                });

        // 배송정보 생성
        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        //주문상품 생성
        List<OrderItem> orderItems = requestOrder.stream().map(o -> {
            Item item = itemRepository.findById(o.getItemId())
                    .orElseThrow(NotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, o.getCount());
            return orderItem;
        }).collect(Collectors.toList());

        Order makeOrder = Order.createOrder(member, orderItems, delivery);

        orderRepository.save(makeOrder);
    }

    @Transactional
    @Override
    public void cancel(String userId, Long orderId) {
        Order findOrder = findOrder(orderId);

        System.out.println("요청한 유저의 아이디 ={}" + userId);
        System.out.println("주문한 유저의 아이디 ={}" + findOrder.getMember().getUserId());
        if(!checkOrderUser(findOrder.getMember(),userId)){
            // 에러 : 주문한 유저와 삭제 요청한 유저의 계정이 다르다.
            throw new NotFoundException();
        }

        findOrder.cancel();
    }

    @Transactional
    @Override
    public void delete(String userId, Long orderId) {
        Order findOrder = findOrder(orderId);

        System.out.println("요청한 유저의 아이디 ={}" + userId);
        System.out.println("주문한 유저의 아이디 ={}" + findOrder.getMember().getUserId());
        if(!checkOrderUser(findOrder.getMember(),userId)){
            // 에러 : 주문한 유저와 삭제 요청한 유저의 계정이 다르다.
            throw new NotFoundException();
        }

        if(findOrder.getOrderStatus() != OrderStatus.CANCEL){
            throw new IllegalStateException("주문취소 이후 삭제가 가능합니다.");
        }

        orderRepository.deleteById(orderId);
    }

    /**
     * 주문을 등록한 계정과 해당 요청의 계정을 확인하는 메소드
     */
    private boolean checkOrderUser(Member member, String requestUserId){
        if (member.getUserId().equals(requestUserId)) {
            return true;
        }
        return false;
    }

    /**
     * 주문 조회
     */
    private Order findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
    }
}
