package com.errorCode.pandaOffice.notice.presectation;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.dto.request.CreateNoticeRequestDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeImageResponseDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeResponseDTO;
import com.errorCode.pandaOffice.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }



    // 조회수 증가
    @PutMapping("/{noticeId}/incrementViewCount")
    public void incrementViewCount(@PathVariable int noticeId) {
        noticeService.incrementViewCount(noticeId);
    }

    // 공지사항 등록
    @PostMapping("")  // ("") = ("/notice")
    public ResponseEntity<Void> createNotice(@RequestPart CreateNoticeRequestDTO createNoticeRequestDTO,
                                             @RequestParam(required = false)List<MultipartFile> imageList) {
        int noticeId = noticeService.createNotice(createNoticeRequestDTO, imageList);
        return ResponseEntity.created(URI.create("noticeId")).build();
    }


    // 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public void deleteNotice(@PathVariable int noticeId) {
        noticeService.deleteNotice(noticeId);
    }



    // 이미지 삭제
    @DeleteMapping("/image")
    /* 요청을 보낼때 local~~~/notice/image?imageId=1&noticeId=2 */
    public void removeImage(@RequestParam int imageId, @RequestParam int noticeId) {
//        noticeService.removeImage(noticeId, imageId);
    }
}
