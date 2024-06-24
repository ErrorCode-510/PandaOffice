package com.errorCode.pandaOffice.attendance.domain.repository;

import com.errorCode.pandaOffice.attendance.domain.entity.OvertimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvertimeRecordRepository extends JpaRepository<OvertimeRecord, Integer> {

    List<OvertimeRecord> findByEmployeeId(int employeeId);

}
