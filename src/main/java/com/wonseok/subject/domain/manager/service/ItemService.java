package com.wonseok.subject.domain.manager.service;

import com.wonseok.subject.domain.manager.dto.item.ItemDto;

public interface ItemService {
    public void create(ItemDto itemDto);
    public void delete(Long itemId);
    public void update(Long itemId, ItemDto itemDto);
}
