package com.errorCode.pandaOffice.employee.domain.repository;


import com.errorCode.pandaOffice.employee.domain.entity.EmployeePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeePhotoRepository extends JpaRepository<EmployeePhoto, Long> {
    Optional<EmployeePhoto> findByEmployeeEmployeeId(int employeeId);
}
