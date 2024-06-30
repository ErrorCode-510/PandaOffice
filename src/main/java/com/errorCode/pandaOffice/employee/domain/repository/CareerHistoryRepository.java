package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.CareerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerHistoryRepository extends JpaRepository<CareerHistory, Long> {
}