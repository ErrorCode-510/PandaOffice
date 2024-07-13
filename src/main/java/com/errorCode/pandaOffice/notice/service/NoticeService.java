package com.errorCode.pandaOffice.notice.service;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;


// 공지사항 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final EmployeeRepository employeeRepository;

    // 페이징 정보를 반환하는 메소드
    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("noticeId").descending());
    }

    // 전체 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @Transactional(readOnly = true)
    public Page<NoticeResponseDTO> getAllNotices(final int page) {
        Page<Notice> notices = noticeRepository.findAllNotices(getPageable(page));
        return notices.map(NoticeResponseDTO::from);
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @Transactional(readOnly = true)
    public Page<NoticeResponseDTO> getNoticesByCategoryAndSubCategory(String category, String subCategory, int page) {
        Page<Notice> notices;

        if ((category == null || category.isEmpty()) && (subCategory == null || subCategory.isEmpty())) {
            // 전체 조회
            notices = noticeRepository.findAll(getPageable(page));
        } else if (category != null && !category.isEmpty() && (subCategory == null || subCategory.isEmpty())) {
            // 카테고리로 조회
            notices = noticeRepository.findByCategory(category, getPageable(page));
        } else if (category != null && !category.isEmpty() && subCategory != null && !subCategory.isEmpty()) {
            // 카테고리와 서브카테고리로 조회
            notices = noticeRepository.findByCategoryAndSubCategory(category, subCategory, getPageable(page));
        } else {
            // 기타 경우
            notices = Page.empty(); // 빈 페이지 반환
        }

        return notices.map(NoticeResponseDTO::from);
    }

    // 특정 공지사항 조회
    @Transactional(readOnly = true)
    public NoticeResponseDTO getNoticeById(final int noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
        return NoticeResponseDTO.from(notice);
    }

    // 조회수 증가
    public void incrementViewCount(int noticeId) {
        if (noticeRepository.existsById(noticeId)) {
            noticeRepository.incrementViewCount(noticeId);
        }
    }

    // 공지사항 등록 메소드
    @Transactional
    public Integer createNotice(final NoticeRequestDTO noticeRequestDTO) {

        /* 작성자 정보 가져오기 */
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow();

        /* 공지사항 생성 및 저장 */
        Notice newNotice = Notice.of(noticeRequestDTO, employee);
        Notice saveNotice = noticeRepository.save(newNotice);

        return saveNotice.getNoticeId();
    }

    // 공지사항 수정 메소드
//    public void modifyNotice(final int noticeId, final NoticeRequestDTO noticeRequestDTO) {
//        Notice notice = noticeRepository.findById(noticeId).orElseThrow();
//
//        // 비공개 설정은 팀장 이상만 가능하도록 확인
//        char status = notice.getStatus();
//        if (noticeRequestDTO.getStatus() != '\0' && noticeRequestDTO.getStatus() == 'N') {
//
//            status = noticeRequestDTO.getStatus();
//        }
//
//        notice.updateNotice(
//                noticeRequestDTO.getTitle(),
//                noticeRequestDTO.getContent(),
//                noticeRequestDTO.getCategory(),
//                noticeRequestDTO.getSubCategory(),
//                noticeRequestDTO.getViewCount(),
//                status,
//                notice.getEmployeeId()
//        );
//        noticeRepository.save(notice);
//    }

    // 공지사항 삭제 메소드
    public void deleteNotice(final int noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoSuchElementException::new);
        noticeRepository.delete(notice);
    }

}
