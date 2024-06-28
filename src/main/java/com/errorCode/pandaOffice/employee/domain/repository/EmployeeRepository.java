package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findFirstByDepartment_IdAndJob_IdOrderByHireDateDesc(Integer departmentId, Integer jobId);

    List<Employee> findByDepartmentId(int departmentId);

    List<Employee> findByJobId(int jobId);

    List<Employee> findByNameContaining(String name);
}
