package com.wonseok.subject.domain.manager.repository;

import com.wonseok.subject.domain.user.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
