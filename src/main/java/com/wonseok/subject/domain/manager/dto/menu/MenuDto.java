package com.wonseok.subject.domain.manager.dto.menu;

import com.wonseok.subject.domain.user.entity.Menu;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
public class MenuDto {
    Long menuId;
    String name;
    int level;
    private String path;
    private Integer sort;
    private Long parentId;

    private List<MenuDto> subMenus;

    private boolean activated;

    // Entity -> Dto
    public static MenuDto from(Menu menu) {
        if (menu == null) return null;

        return MenuDto.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .parentId(menu.getParentId())
                .activated(menu.isActivated())
                .subMenus(
                        menu.getSubMenu()
                                .stream()
                                .map(MenuDto::from)
                                .collect(Collectors.toList()))
                .level(menu.getLevel())
                .path(menu.getPath())
                .sort(menu.getSort())
                .build();
    }

    // Dto -> Entity
    public static Menu toEntity(MenuDto menuDto) {
        return Menu.builder()
                .name(menuDto.name)
                .parentId(menuDto.parentId)
                .activated(menuDto.activated)
                .level(menuDto.level)
                .path(menuDto.path)
                .sort(menuDto.sort)
                .build();
    }
}
