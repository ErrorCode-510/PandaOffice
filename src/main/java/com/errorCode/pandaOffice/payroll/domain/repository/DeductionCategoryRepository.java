package com.errorCode.pandaOffice.payroll.domain.repository;

import com.errorCode.pandaOffice.payroll.domain.entity.DeductionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeductionCategoryRepository extends JpaRepository<DeductionCategory, Integer> {

}
