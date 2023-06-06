package com.wonseok.subject.domain.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name")
    private String name;

    @Column(name = "activated", columnDefinition = "TINYINT")
    private boolean activated;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "level")
    private Integer level;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
        @JoinColumn(name = "parent_id", referencedColumnName = "menu_id", insertable = false, updatable = false)
    })
    private Menu parentMenu;

    @OneToMany(mappedBy = "parentMenu" ,fetch = FetchType.LAZY)
    private List<Menu> subMenu = new ArrayList<>();
}
