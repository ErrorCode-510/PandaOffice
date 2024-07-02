package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.DepartmentBox;
import com.errorCode.pandaOffice.employee.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentBoxRepository extends JpaRepository<DepartmentBox, Integer> {
    List<DepartmentBox> findByDepartment(Department department);
}
