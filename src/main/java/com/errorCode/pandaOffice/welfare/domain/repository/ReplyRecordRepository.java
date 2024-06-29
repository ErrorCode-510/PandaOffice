package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;
import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRecordRepository extends JpaRepository<ReplyRecord,Integer> {
    Optional<ReplyRecord> findBySurveyAndEmployee(Survey survey, Employee employee);
}
