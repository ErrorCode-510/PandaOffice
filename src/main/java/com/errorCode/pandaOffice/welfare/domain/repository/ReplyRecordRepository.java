package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;
import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReplyRecordRepository extends JpaRepository<ReplyRecord,Integer> {
    Optional<ReplyRecord> findBySurveyAndEmployeeAndQuestion(Survey survey, Employee employee, SurveyQuestion question);

    List<ReplyRecord> findBySurveyId(int surveyId);

    @Query("SELECT COUNT(DISTINCT r.employee.employeeId) FROM ReplyRecord r WHERE r.survey.id = :surveyId")
    int countDistinctBySurveyId(@Param("surveyId") int surveyId);
}
