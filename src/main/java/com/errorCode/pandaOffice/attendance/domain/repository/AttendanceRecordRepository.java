package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AttendanceRecord;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    List<AttendanceRecord> findByEmployee_EmployeeIdAndDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);

    Optional<AttendanceRecord> findByDateAndEmployee(LocalDate date, Employee employee);
}
