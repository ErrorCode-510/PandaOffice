package com.errorCode.pandaOffice.payroll.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.payroll.domain.entity.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<PayrollRecord, Integer> {
    PayrollRecord findByEmployee(Employee employee);
}
