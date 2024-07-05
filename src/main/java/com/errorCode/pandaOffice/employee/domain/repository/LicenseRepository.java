package com.errorCode.pandaOffice.employee.domain.repository;


import com.errorCode.pandaOffice.employee.domain.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Long> {
    List<License> findByEmployeeEmployeeId(int employeeId);

    void deleteByEmployeeEmployeeId(int employeeId);
}
