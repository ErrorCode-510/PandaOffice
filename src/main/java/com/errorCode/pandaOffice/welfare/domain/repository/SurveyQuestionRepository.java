package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Integer> {
    List<ReplyRecord> findBySurveyId(int surveyId);
}
