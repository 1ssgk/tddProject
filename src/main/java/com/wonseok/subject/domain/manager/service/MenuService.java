package com.wonseok.subject.domain.manager.service;


import com.wonseok.subject.domain.manager.dto.menu.MenuDto;

import java.util.List;

public interface MenuService {

    void create(List<MenuDto> menuList);

    void update(List<MenuDto> menuList);

    void delete(MenuDto menuDto);

    List<MenuDto> findAll();
}
