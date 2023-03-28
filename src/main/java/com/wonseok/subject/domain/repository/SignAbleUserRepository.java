package com.wonseok.subject.domain.repository;

import com.wonseok.subject.domain.entity.SignAbleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignAbleUserRepository extends JpaRepository<SignAbleUser, Long> {
    boolean existsSignAbleUsersByRegNoAndUserNm(String regNo, String Usernm);
}
