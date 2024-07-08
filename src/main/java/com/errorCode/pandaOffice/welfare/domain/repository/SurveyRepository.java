package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByCategoryId(int categoryId);
}
