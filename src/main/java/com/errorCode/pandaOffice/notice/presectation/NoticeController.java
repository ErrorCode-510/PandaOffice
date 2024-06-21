package com.errorCode.pandaOffice.notice.presectation;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.dto.request.NoticeImageRequestDTO;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 전체 공지사항 조회 (페이징 및 정렬)
    @GetMapping("/list")
    public Page<NoticeResponseDTO> getAllNotices(Pageable pageable) {
        Pageable sortedByDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("postedDate").descending());
        return noticeService.getAllNotices(sortedByDateDesc).map(this::convertToResponseDTO);
    }

    private NoticeResponseDTO convertToResponseDTO(Notice notice) {
        // 이미지 URL 의 기본 경로 설정
        String imageUrlBase = "your-image-url-base/";

        return new NoticeResponseDTO (
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCategory(),
                notice.getSubCategory(),
                notice.getPostedDate(),
                notice.getViewCount(),
                notice.getStatus(),
                notice.getEmployeeId(),
                "employeeName",
                "employeeJob",

                notice.getImages().stream().map(image -> new NoticeImageResponseDTO (
                        image.getImageId(),
                        image.getPath(),
                        image.getName(),
                        image.getExtention(),
                        imageUrlBase + image.getPath()  // imageUrl 필드를 동적으로 생성
                )).collect(Collectors.toList())
        );
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬)
    @GetMapping("/category/{category}/{subCategory}")
    public Page<NoticeResponseDTO> getNoticesByCategory(@PathVariable String category, @PathVariable String subCategory, Pageable pageable) {
        Pageable sortedByDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("postedDate").descending());
        return noticeService.getNoticesByCategory(category, subCategory, sortedByDateDesc).map(this::convertToResponseDTO);
    }

    // 특정 공지사항 조회
    @GetMapping("/{noticeId}")
    public NoticeResponseDTO getNoticeById(@PathVariable int noticeId) {
        return convertToResponseDTO(noticeService.getNoticeById(noticeId));
    }

    // 조회수 증가
    @PutMapping("/{noticeId}/incrementViewCount")
    public void incrementViewCount(@PathVariable int noticeId) {
        noticeService.incrementViewCount(noticeId);
    }

    // 공지사항 등록
    @PostMapping("")  // ("") = ("/notice")
    public Notice createNotice(@RequestBody NoticeRequestDTO noticeRequestDTO) {
        return noticeService.createNotice(noticeRequestDTO);
    }

    // 공지사항 수정 (공개여부 Y/N)
    @PutMapping("/{noticeId}")
    public Notice updateNotice(@PathVariable int noticeId, @RequestBody NoticeRequestDTO noticeRequestDTO) {
        return noticeService.updateNotice(noticeId,noticeRequestDTO);
    }

    // 공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public void deleteNotice(@PathVariable int noticeId) {
        noticeService.deleteNotice(noticeId);
    }

    // 이미지 업로드
    @PostMapping("/image")
    public ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile file,
                                            @RequestParam("noticeId") int noticeId) {

        String extention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        NoticeImageRequestDTO noticeImageRequestDTO = new NoticeImageRequestDTO (
                noticeId,
                file.getOriginalFilename(),
                extention,
                "uploads/" + noticeId + "/"
        );

        noticeService.uploadImage(file, noticeImageRequestDTO);
        return ResponseEntity.ok().build();
    }

    // 이미지 삭제
    @DeleteMapping("/image")
    /* 요청을 보낼때 local~~~/notice/image?imageId=1&noticeId=2 */
    public void removeImage(@RequestParam int imageId, @RequestParam int noticeId) {
        noticeService.removeImage(noticeId, imageId);
    }
}
