package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnualLeaveRecordRepository extends JpaRepository<AnnualLeaveRecord, Integer> {
    List<AnnualLeaveRecord> findByEmployee_EmployeeId(int employeeId);

    @Query("SELECT alr " +
            "FROM AnnualLeaveRecord as alr " +
            "JOIN alr.annualLeaveCategory as ac " +
            "WHERE alr.employee.employeeId  = :employeeId")
    List<AnnualLeaveRecord> findByEmployeeIdWithCategory(@Param("employeeId") int employeeId);
}
