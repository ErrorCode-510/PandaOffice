package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveUsedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnnualLeaveUsedRecordRepository extends JpaRepository<AnnualLeaveUsedRecord, Integer> {
    List<AnnualLeaveUsedRecord> findByEmployee_EmployeeIdAndUsedStartDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);
    List<AnnualLeaveUsedRecord> findByEmployee_HireDateBetween(LocalDate startOfHiredYear, LocalDate endOfHiredYear);
    List<AnnualLeaveUsedRecord> findByUsedStartDateBetween(LocalDate startDate, LocalDate endDate);
}
