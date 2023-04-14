package com.wonseok.subject.domain.manager.dto.item;

import com.wonseok.subject.domain.user.entity.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
public class ItemDto {
    private Long brandId;
    private Long categoryId;
    private String name;
    private int price;
    private int stockQuantity;

    public static Item toEntity(ItemDto createItemDto){
        return Item.builder()
                .name(createItemDto.getName())
                .price(createItemDto.getPrice())
                .stockQuantity(createItemDto.getStockQuantity())
                .build();
    }
}
