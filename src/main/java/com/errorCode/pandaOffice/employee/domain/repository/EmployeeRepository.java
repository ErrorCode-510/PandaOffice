package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findFirstByDepartment_IdAndJob_IdOrderByHireDateDesc(Integer departmentId, Integer jobId);

    List<Employee> findByDepartmentId(int departmentId);

    List<Employee> findByJobId(int jobId);

    List<Employee> findByNameContaining(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.email = :email WHERE e.employeeId = :employeeId")
    void updateEmail(int employeeId, String email);

    @Transactional
    @Modifying
    @Query("UPDATE Employee e SET e.selfIntroduction = :selfIntroduction WHERE e.employeeId = :employeeId")
    void updateSelfIntroduction(int employeeId, String selfIntroduction);


    Employee findFirstByJob_Title(String jobTitle);
}
