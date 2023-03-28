package com.wonseok.subject.domain.repository;

import com.wonseok.subject.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
