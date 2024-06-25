package com.errorCode.pandaOffice.notice.domain.repository;

import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 공지사항 이미지 리포지토리 인터페이스
@Repository
public interface NoticeImageRepository extends JpaRepository<NoticeImage, Integer> {
}
