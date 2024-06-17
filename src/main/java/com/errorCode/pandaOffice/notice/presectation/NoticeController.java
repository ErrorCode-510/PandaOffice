package com.errorCode.pandaOffice.notice.presectation;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 전체 공지사항 조회 (페이징 및 정렬)
    @GetMapping
    public Page<Notice> getAllNotices(Pageable pageable) {
        return noticeService.getAllNotices(pageable);
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬)
    @GetMapping("/category/{category}/{subCategory}")
    public Page<Notice> getNoticesByCategory(@PathVariable String category, @PathVariable String subCategory, Pageable pageable) {
        return noticeService.getNoticesByCategory(category, subCategory, pageable);
    }

    // 특정 공지사항 조회 및 조회수 증가
    @GetMapping("/{id}")
    public Notice getNoticeById(@PathVariable int id) {
        try {
            return noticeService.getNoticeById(id);
        } catch (NoticeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // 공지사항 등록
    @PostMapping
    public Notice createNotice(@RequestBody Notice notice) {
        return noticeService.createNotice(notice);
    }

    // 공지사항 수정 (공개여부 Y/N)
    @PutMapping("/{id}")
    public Notice updateNotice(@PathVariable int id, @RequestBody Notice updatedNotice) {
        try {
            return noticeService.updateNotice(id,updatedNotice);
        } catch (NoticeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable int id) {
        noticeService.deleteNotice(id);
    }
}
