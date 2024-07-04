package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.OverTimeAndLatenessRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OverTimeAndLatenessRecordRepository extends JpaRepository<OverTimeAndLatenessRecord, Integer> {

    List<OverTimeAndLatenessRecord> findByEmployee_EmployeeIdAndDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);
}
