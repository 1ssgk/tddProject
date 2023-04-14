package com.wonseok.subject.domain.user.dto.order;

import com.wonseok.subject.domain.user.entity.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrderItemDto {
    private Long orderItemId;
    private int count;
    private int price;
    private String name;


    public static OrderItem toEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .id(orderItemDto.getOrderItemId())
                .count(orderItemDto.getCount())
                .orderPrice(orderItemDto.getPrice())
                .build();
    }
}
