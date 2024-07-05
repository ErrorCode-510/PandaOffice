package com.errorCode.pandaOffice.payroll.domain.repository;

import com.errorCode.pandaOffice.payroll.domain.entity.DeductionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionRepository extends JpaRepository<DeductionRecord, Integer> {

}
