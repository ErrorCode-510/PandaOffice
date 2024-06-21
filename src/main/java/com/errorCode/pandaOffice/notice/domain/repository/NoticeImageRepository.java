package com.errorCode.pandaOffice.notice.domain.repository;

import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeImageRepository extends JpaRepository<NoticeImage, Integer> {
}
