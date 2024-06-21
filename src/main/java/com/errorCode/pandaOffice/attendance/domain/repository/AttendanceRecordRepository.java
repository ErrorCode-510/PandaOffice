package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> findByEmployeeId(int employeeId);
}
