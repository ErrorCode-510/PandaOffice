package com.errorCode.pandaOffice.employee.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findFirstByDepartment_IdAndJob_IdOrderByHireDateDesc(Integer departmentId, Integer jobId);

    // 부서 ID로 사원을 조회
    List<Employee> findByDepartmentId(int departmentId);

    // 직급 ID로 사원을 조회
    List<Employee> findByJobId(int jinId);

    // [검색기능] 이름을 포함한 사원 조회
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> findByNameContaining(@Param("name") String name);
}
