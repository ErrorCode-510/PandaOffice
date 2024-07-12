package com.errorCode.pandaOffice.welfare.domain.repository;

import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    // 특정 카테고리 ID에 해당하는 모든 설문을 조회
    List<Survey> findByCategoryId(int categoryId);

    // 주어진 설문 ID와 날짜 범위 내에 포함되는 설문을 조회
    // 설문 시작 날짜가 startDate보다 작거나 같고, 종료 날짜가 endDate보다 크거나 같은 경우 해당
    Optional<Survey> findByIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(int id, LocalDate startDate, LocalDate endDate);

    // 주어진 날짜 범위 내에 포함되는 첫 번째 설문을 조회
    // 설문 시작 날짜가 startDate보다 작거나 같고, 종료 날짜가 endDate보다 크거나 같은 경우 해당
    Optional<Survey> findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    // 주어진 시작 날짜를 가진 설문이 존재하는지 확인
    boolean existsByStartDate(LocalDate startDate);

    // 새로운 메서드 추가: 종료된 설문 중 가장 최근 설문을 조회
    @Query("SELECT s FROM Survey s WHERE s.endDate < :currentDate ORDER BY s.endDate DESC")
    List<Survey> findMostRecentEndedSurvey(@Param("currentDate") LocalDate currentDate);
}

