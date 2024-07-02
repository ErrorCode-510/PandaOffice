package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationHistoryRepository extends JpaRepository<EducationHistory, Long> {
    List<EducationHistory> findByEmployeeEmployeeId(int employeeId);
}