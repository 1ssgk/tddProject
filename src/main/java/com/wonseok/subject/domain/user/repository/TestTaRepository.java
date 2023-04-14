package com.wonseok.subject.domain.user.repository;

import com.wonseok.subject.domain.user.entity.TestTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TestTaRepository extends JpaRepository<TestTa, Long> {


    @Modifying(clearAutomatically = true)
    @Query("update TestTa t set t.name =:name where t.id =:id")
    int updateName(@Param("id") Long id, @Param("name") String name);

}
