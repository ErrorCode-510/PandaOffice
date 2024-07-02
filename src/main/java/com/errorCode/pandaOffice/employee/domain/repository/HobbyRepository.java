package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {

    // 특정 사원의 ID를 기반으로 취미를 조회
    @Query("SELECT h FROM Hobby h WHERE h.employee.employeeId = :employeeId")
    List<Hobby> findByEmployee(@Param("employeeId") int employeeId);

    // 특정 사원의 ID를 기반으로 취미를 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM Hobby h WHERE h.employee.employeeId = :employeeId")
    void deleteByEmployee(int employeeId);
}
