package com.errorCode.pandaOffice.notice.service;

import com.errorCode.pandaOffice.common.exception.NotFoundException;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// 공지사항 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    @Value("${image.image-url}")
    private String IMAGE_URL;

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    private final NoticeRepository noticeRepository;
    private final EmployeeRepository employeeRepository;

    // 페이징 정보를 반환하는 메소드
    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("noticeId").descending());
    }

    // 전체 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @Transactional(readOnly = true)
    public Page<NoticeResponseDTO> getAllNotices(final int page) {
        Page<Notice> notices = noticeRepository.findAll(getPageable(page));
        return notices.map(notice -> NoticeResponseDTO.from(notice));
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @Transactional(readOnly = true)
    public Page<NoticeResponseDTO> getNoticesByCategory(final String category, final String subCategory, final Integer page) {
        Page<Notice> notices = noticeRepository.findByCategoryAndSubCategory(category, subCategory, getPageable(page));
        return notices.map(notice -> NoticeResponseDTO.from(notice));
    }

    // 특정 공지사항 조회
    @Transactional(readOnly = true)
    public NoticeResponseDTO getNoticeById(final int noticeId) {
        Optional<Notice> noticeOptional = noticeRepository.findById(noticeId);
        return noticeOptional.map(notice -> NoticeResponseDTO.from(notice)).orElseThrow();
    }

    // 조회수 증가
    public void incrementViewCount(int noticeId) {
        if (noticeRepository.existsById(noticeId)) {
            noticeRepository.incrementViewCount(noticeId);
        }
    }

    // 공지사항 등록 메소드
    public Integer createNotice(
            final NoticeRequestDTO noticeRequestDTO
    ) {

        final Notice newNotice = Notice.of(
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                noticeRequestDTO.getPostedDate(),
                noticeRequestDTO.getViewCount(),
                noticeRequestDTO.getStatus(),
                noticeRequestDTO.getEmployeeId()
        );

        final Notice notice = noticeRepository.save(newNotice);

        return notice.getNoticeId();
    }

    // 공지사항 수정 메소드
    public void modifyNotice(final int noticeId, final NoticeRequestDTO noticeRequestDTO) {
        Notice notice =  noticeRepository.findById(noticeId).orElseThrow();

       notice.updateNotice (
               noticeRequestDTO.getTitle(),
               noticeRequestDTO.getContent(),
               noticeRequestDTO.getCategory(),
               noticeRequestDTO.getSubCategory(),
               noticeRequestDTO.getPostedDate(),
               noticeRequestDTO.getViewCount(),
               noticeRequestDTO.getStatus(),
               noticeRequestDTO.getEmployeeId()
       );
       noticeRepository.save(notice);
    }

    // 공지사항 삭제 메소드
    @Transactional
    public void deleteNotice(final int noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);

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
