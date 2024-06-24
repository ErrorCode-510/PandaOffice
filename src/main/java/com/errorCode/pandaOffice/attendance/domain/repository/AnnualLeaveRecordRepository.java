package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualLeaveRecordRepository extends JpaRepository<AnnualLeaveRecord, Integer> {
    List<AnnualLeaveRecord> findByEmployeeId(int employeeId);
}
