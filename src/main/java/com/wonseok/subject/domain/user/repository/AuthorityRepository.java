package com.wonseok.subject.domain.user.repository;

import com.wonseok.subject.domain.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
