package com.errorCode.pandaOffice.notice.presectation;

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
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

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

    // 카테고리 별 공지사항 조회 (페이징 처리)
    @GetMapping("/category/{category}/{subCategory}")
    public ResponseEntity<PagingResponse> getNoticeByCategory(
            @PathVariable String category,
            @PathVariable String subCategory,
            @RequestParam(defaultValue = "1") final Integer page
    ) {
        final Page<NoticeResponseDTO> notices = noticeService.getNoticesByCategory(category, subCategory, page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(notices);
        final PagingResponse pagingResponse = PagingResponse.of(notices.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    // 특정 공지사항 조회
    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeResponseDTO> getNoticeById(@PathVariable int noticeId) {
        final NoticeResponseDTO noticeResponse = noticeService.getNoticeById(noticeId);

        if (noticeResponse != null) {
            return ResponseEntity.ok(noticeResponse);
        } else {
            return ResponseEntity.notFound().build();
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
            @RequestBody @Valid final NoticeRequestDTO noticeRequestDTO
    ) {
        if (noticeRequestDTO.getEmployeeId() == null) {
            return ResponseEntity.badRequest().build();
        }

        final Integer noticeId = noticeService.createNotice(noticeRequestDTO);

        return ResponseEntity.created(URI.create("/notice/regist/" + noticeId)).build();
    }

    // 공지사항 수정
//    @PutMapping("/notices/{noticeId}")
//    public ResponseEntity<Void> modifyNotice(
//            @PathVariable final int noticeId,
//            @RequestBody @Valid final NoticeRequestDTO noticeRequestDTO
//    ) {
//        noticeService.modifyNotice(noticeId, noticeRequestDTO);
//
//        return ResponseEntity.noContent().build();
//    }

    // 공지사항 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable int noticeId) {
        noticeService.deleteNotice(noticeId);

        return ResponseEntity.noContent().build();
    }
}
