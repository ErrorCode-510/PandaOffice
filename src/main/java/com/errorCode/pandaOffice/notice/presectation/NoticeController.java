package com.errorCode.pandaOffice.notice.presectation;
import com.errorCode.pandaOffice.common.exception.NotFoundException;
import com.errorCode.pandaOffice.common.paging.Pagination;
import com.errorCode.pandaOffice.common.paging.PagingButtonInfo;
import com.errorCode.pandaOffice.common.paging.PagingResponse;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeResponseDTO;
import com.errorCode.pandaOffice.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;


// 공지사항 컨트롤러 클래스
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    // 전체 공지사항 조회
    @GetMapping("/notices")
    public ResponseEntity<PagingResponse> getAllNotices(
            @RequestParam(defaultValue = "1") final Integer page
    ) {
        final Page<NoticeResponseDTO> notices = noticeService.getAllNotices(page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(notices);
        final PagingResponse pagingResponse = PagingResponse.of(notices.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    // 카테고리 서브카테고리 별 공지사항 조회 (페이징 처리)(사이드바)
    @GetMapping("/category/filter")
    public ResponseEntity<PagingResponse> getNoticesByCategoryAndSubCategory(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subCategory,
            @RequestParam(defaultValue = "1") final Integer page
    ) {
        final Page<NoticeResponseDTO> notices = noticeService.getNoticesByCategoryAndSubCategory(category, subCategory, page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(notices);
        final PagingResponse pagingResponse = PagingResponse.of(notices.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    // 특정 공지사항 조회
    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeResponseDTO> getNoticeById(@PathVariable int noticeId) {
        try {
            final NoticeResponseDTO noticeResponse = noticeService.getNoticeById(noticeId);
            return ResponseEntity.ok(noticeResponse);
        } catch (SecurityException e) {
            return ResponseEntity.notFound().build();  // 공지사항을 찾을 수 없음
        }
    }

    // 조회수 증가
    @PutMapping("/{noticeId}/incrementViewCount")
    public void incrementViewCount(@PathVariable int noticeId) {
        noticeService.incrementViewCount(noticeId);
    }

    // 공지사항 등록
    @PostMapping("/regist")
    public ResponseEntity<Void> createNotice(
            @RequestBody @Valid final NoticeRequestDTO noticeRequestDTO) {

        final Integer noticeId = noticeService.createNotice(noticeRequestDTO);
        return ResponseEntity.created(URI.create("/notice/regist/" + noticeId)).build();
    }

    // 공지사항 수정
//    @PutMapping("/notices/{noticeId}")
//    public ResponseEntity<Void> modifyNotice(
//            @PathVariable final int noticeId,
//            @RequestBody @Valid final NoticeRequestDTO noticeRequestDTO
//    ) {
//        try {
//            noticeService.modifyNotice(noticeId, noticeRequestDTO);
//            return ResponseEntity.noContent().build();  // 수정 성공 시 204 No Content 응답
//        } catch (NotFoundException e) {
//            return ResponseEntity.notFound().build();  // 공지사항을 찾을 수 없을 때 404 Not Found 응답
//        }
//    }

    // 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable int noticeId) {
        try {
            noticeService.deleteNotice(noticeId);
            return ResponseEntity.noContent().build();  // 삭제 성공 시 204 No Content 응답
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();  // 공지사항을 찾을 수 없을 때 404 Not Found 응답
        }
    }
}
