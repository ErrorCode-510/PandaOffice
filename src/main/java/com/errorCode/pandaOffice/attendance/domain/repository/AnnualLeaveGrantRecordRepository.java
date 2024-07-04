package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveGrantRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AnnualLeaveGrantRecordRepository extends JpaRepository<AnnualLeaveGrantRecord, Integer> {
    List<AnnualLeaveGrantRecord> findByEmployee_EmployeeIdAndDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);

    List<AnnualLeaveGrantRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<AnnualLeaveGrantRecord> findByEmployee_NameInAndDateBetween(List<String> employeeNames, LocalDate currentYearStart, LocalDate currentYearEnd);
}
