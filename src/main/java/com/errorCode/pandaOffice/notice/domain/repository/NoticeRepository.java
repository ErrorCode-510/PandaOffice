package com.errorCode.pandaOffice.notice.domain.repository;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    // 전체 공지사항을 최신순으로 조회
    Page<Notice> findAllByOrderByPostedDateDesc(Pageable pageable);

    // 분류와 소분류별 공지사항을 최신순으로 조회
    Page<Notice> findByCategoryAndSubCategoryOrderByPostedDateDesc(String category, String subCategory, Pageable pageable);

    Page<Notice> findByCategoryAndSubCategory(String category, String subCategory, Pageable pageable);
}
