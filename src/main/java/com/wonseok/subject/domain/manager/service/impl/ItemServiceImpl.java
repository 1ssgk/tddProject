package com.wonseok.subject.domain.manager.service.impl;

import com.wonseok.subject.domain.common.exception.NotFoundException;
import com.wonseok.subject.domain.manager.repository.BrandRepository;
import com.wonseok.subject.domain.manager.repository.CategoryRepository;
import com.wonseok.subject.domain.user.entity.Brand;
import com.wonseok.subject.domain.user.entity.Item;
import com.wonseok.subject.domain.manager.dto.item.ItemDto;
import com.wonseok.subject.domain.manager.repository.ItemRepository;
import com.wonseok.subject.domain.manager.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public void create(ItemDto itemDto) {
        Item item = Item.builder()
                .name(itemDto.getName())
                .stockQuantity(itemDto.getStockQuantity())
                .price(itemDto.getPrice())
                .build();

        item.setBrand(itemDto.getBrandId());
        item.setCategory(itemDto.getCategoryId());

        itemRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    @Override
    public void update(Long itemId, ItemDto itemDto) {
        // 아이템 검색
        Item findItem = itemRepository.findById(itemId).orElseThrow(() -> {
            //해당 아이템이 존재하지 않습니다.
            throw new NotFoundException();
        });

        findItem.changeName(itemDto.getName());
        findItem.changePrice(itemDto.getPrice());
        findItem.changeQuantity(itemDto.getStockQuantity());
    }
}
