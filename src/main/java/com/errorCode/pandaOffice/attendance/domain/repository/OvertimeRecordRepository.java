package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.OvertimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, Integer> {

    List<OvertimeRecord> findByEmployee_EmployeeIdAndDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);


}
