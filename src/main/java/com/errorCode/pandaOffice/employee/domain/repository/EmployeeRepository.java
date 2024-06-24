package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Object> findFirstByDepartmentIdAndJobIdOrderByEmployeeIdAsc(Integer departmentId, Integer jobId);
}
