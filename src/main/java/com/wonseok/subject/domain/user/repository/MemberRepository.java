package com.wonseok.subject.domain.user.repository;

import java.util.Optional;

import com.wonseok.subject.domain.user.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUserId(String userId);

    Optional<Member> findOneMemberByMemberId(String memberId);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByUserId(String memberId);


    Optional<Member> findByUserId(String userId);
}

