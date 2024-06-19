package com.errorCode.pandaOffice.notice.service;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    // 생성자 주입을 통해 NoticeRepository 주입받음
    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 전체 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    public Page<Notice> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    public Page<Notice> getNoticesByCategory(String category, String subCategory, Pageable pageable) {
        return noticeRepository.findByCategoryAndSubCategory(category, subCategory, pageable);
    }

    // 특정 공지사항 조회
    // 지정된 ID의 공지사항을 조회
    public Notice getNoticeById(int id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + id));
    }

    // 조회수 증가
    public void incrementViewCount(int id) {
        Notice notice = noticeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Notice not found with id" + id));
        notice.setViewCount(notice.getViewCount() + 1);
        noticeRepository.save(notice);
    }

    // 공지사항 등록
    // 새로운 공지사항 등록, 등록 시 생성일자와 초기 조회수 설정
    public Notice createNotice(Notice notice) {
        notice.setPostedDate(LocalDate.now());
        notice.setViewCount(0);
        return noticeRepository.save(notice);
    }

    // 공지사항 수정 (지정된 ID의 공지사항 수정 / 제목, 내용, 분류, 소분류, 공개여부)
    public Notice updateNotice(int id, Notice updatedNotice) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + id));
        notice.setTitle(updatedNotice.getTitle());
        notice.setContent(updatedNotice.getContent());
        notice.setCategory(updatedNotice.getCategory());
        notice.setSubCategory(updatedNotice.getSubCategory());
        notice.setStatus(updatedNotice.getStatus());
        return noticeRepository.save(notice);
    }

    // 공지사항 삭제 (지정된 ID의 공지사항 삭제)
    public void deleteNotice(int id) {
        noticeRepository.deleteById(id);
    }

    // 3년이 지난 공지사항 자동 삭제 (매일 자정에 실행, 등록된 지 3년이 지난 공지사항 삭제)
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldNotice() {
        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        List<Notice> oldNotices = noticeRepository.findAll();
        oldNotices.stream()
                .filter(notice -> notice.getPostedDate().isBefore(threeYearsAgo))
                .forEach(noticeRepository::delete);
    }
}
