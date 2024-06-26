package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualLeaveCategoryRepository extends JpaRepository<AnnualLeaveCategory, Integer> {

    AnnualLeaveCategory findByName(String name);
}
