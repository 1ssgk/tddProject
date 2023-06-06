package com.wonseok.subject.domain.manager.repository;

import com.wonseok.subject.domain.user.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByLevel(Integer level, Sort sort);
}
