package com.wonseok.subject.domain.manager.repository;

import com.wonseok.subject.domain.user.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
