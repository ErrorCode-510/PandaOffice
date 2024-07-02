package com.errorCode.pandaOffice.notice.domain.repository;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// 공지사항 리포지토리 인터페이스
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    // 특정 공지사항 조회
    @EntityGraph(attributePaths = {"employee", "employee.job"})
    Optional<Notice> findById(int id);

    // 전체 공지사항을 최신순으로 조회
    @EntityGraph(attributePaths = {"employee", "employee.job"})
    Page<Notice> findAll(Pageable pageable);

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @EntityGraph(attributePaths = {"employee", "employee.job"})
    Page<Notice> findByCategoryAndSubCategory(String category, String subCategory, Pageable pageable);
    Page<Notice> findByStatus(char status, Pageable pageable);

    // 조회수 증가
    @Transactional
    @Modifying
    @Query("UPDATE Notice n SET n.viewCount = n.viewCount + 1 WHERE n.noticeId = :noticeId")
    void incrementViewCount(@Param("noticeId") int noticeId);

}
