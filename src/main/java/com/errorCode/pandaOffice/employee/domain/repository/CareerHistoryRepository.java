package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.CareerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerHistoryRepository extends JpaRepository<CareerHistory, Long> {
    List<CareerHistory> findByEmployeeEmployeeId(int employeeId);

    void deleteByEmployeeEmployeeId(int employeeId);
}