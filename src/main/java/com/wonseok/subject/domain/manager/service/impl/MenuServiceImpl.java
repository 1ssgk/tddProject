package com.wonseok.subject.domain.manager.service.impl;

import com.wonseok.subject.domain.manager.dto.menu.MenuDto;
import com.wonseok.subject.domain.manager.repository.MenuRepository;
import com.wonseok.subject.domain.manager.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    @Override
    public void create(List<MenuDto> menuList) {
        menuRepository
        .saveAll(
            menuList
            .stream()
            .map(MenuDto::toEntity)
            .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public void update(List<MenuDto> menuList) {

    }
    @Transactional
    @Override
    public void delete(MenuDto menuDto) {

    }

    @Override
    public List<MenuDto> findAll() {
        return menuRepository
                .findAllByLevel(0, Sort.by(Sort.Direction.ASC,"sort"))
                .stream()
                .map(MenuDto::from)
                .collect(Collectors.toList());
    }
}
