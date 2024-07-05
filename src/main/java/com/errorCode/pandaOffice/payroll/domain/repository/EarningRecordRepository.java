package com.errorCode.pandaOffice.payroll.domain.repository;

import com.errorCode.pandaOffice.payroll.domain.entity.EarningRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EarningRecordRepository extends JpaRepository<EarningRecord, Integer> {

}
