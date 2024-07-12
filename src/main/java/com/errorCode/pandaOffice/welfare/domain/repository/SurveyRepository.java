package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findByCategoryId(int categoryId);
    Optional<Survey> findByIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(int id, LocalDate startDate, LocalDate endDate);
    Optional<Survey> findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
//    Optional<Survey> findFirstByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
}

