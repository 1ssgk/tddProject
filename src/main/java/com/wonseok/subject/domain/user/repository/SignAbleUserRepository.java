package com.wonseok.subject.domain.user.repository;

import com.wonseok.subject.domain.user.entity.SignAbleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignAbleUserRepository extends JpaRepository<SignAbleUser, Long> {
    boolean existsSignAbleUsersByRegNoAndUserNm(String regNo, String Usernm);
}
