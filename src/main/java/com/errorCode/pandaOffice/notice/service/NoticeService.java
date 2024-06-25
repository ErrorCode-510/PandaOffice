package com.errorCode.pandaOffice.notice.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import com.errorCode.pandaOffice.notice.dto.response.NoticeResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return notices.map(notice -> NoticeResponseDTO.from(notice, IMAGE_URL));
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    @Transactional(readOnly = true)
    public Page<NoticeResponseDTO> getNoticesByCategory(final String category, final String subCategory, final Integer page) {
        Page<Notice> notices = noticeRepository.findByCategoryAndSubCategory(category, subCategory, getPageable(page));
        return notices.map(notice -> NoticeResponseDTO.from(notice, IMAGE_URL));
    }

    // 특정 공지사항 조회
    @Transactional(readOnly = true)
    public NoticeResponseDTO getNoticeById(final int noticeId) {
        Optional<Notice> noticeOptional = noticeRepository.findById(noticeId);
        return noticeOptional.map(notice -> NoticeResponseDTO.from(notice, IMAGE_URL)).orElse(null);
    }

    // 조회수 증가
    public void incrementViewCount(int noticeId) {
        if (noticeRepository.existsById(noticeId)) {
            noticeRepository.incrementViewCount(noticeId);
        }
    }

    // 공지사항 등록 메소드
//    public int createNotice(final Integer employeeId, final NoticeRequestDTO noticeRequestDTO) {
//        // 사번으로 DB에서 사원 엔티티 가져오기
//        Employee employee = employeeRepository.findById(employeeId).
//                orElseThrow( () -> new EntityNotFoundException("사번이 비어있습니다."));
////        if (employee == null) {
////            throw new IllegalArgumentException("Invalid employee ID");
////        }
//
//        // 파일 저장 로직. 파일을 저장하고 원본명, 경로, 타입을 가진 엔티티 리스트를 반환한다.
//        List<NoticeImage> imageEntityList = saveImages(noticeRequestDTO.getImageList());
//        if (imageEntityList == null) {
//            return -1;
//        }
//
//        // 공지사항 엔티티 생성
////        Notice newNotice = Notice.of(employee, imageEntityList, noticeRequestDTO);
//
//        final Notice newNotice = Notice.of(
//            noticeRequestDTO.getTitle(),
//                noticeRequestDTO.getContent(),
//                noticeRequestDTO.getCategory(),
//                noticeRequestDTO.getSubCategory(),
//                noticeRequestDTO.getPostedDate(),
//                noticeRequestDTO.getViewCount(),
//                noticeRequestDTO.getStatus(),
//                noticeRequestDTO.getEmployeeId(),
//                noticeRequestDTO.getImageList()
//        );
//
//
//        // 공지사항 DB 저장
//        noticeRepository.save(newNotice);
//
//        // 생성된 공지사항 ID 반환
//        return newNotice.getNoticeId();
//    }

    // 공지사항 등록 메소드
    public Integer createNotice(final NoticeRequestDTO noticeRequestDTO, final MultipartFile noticeImg) {

        final Notice newNotice = Notice.of(
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                noticeRequestDTO.getPostedDate(),
                noticeRequestDTO.getViewCount(),
                noticeRequestDTO.getStatus(),
                noticeRequestDTO.getEmployeeId(),
                noticeRequestDTO.getImageList()
        );

        final Notice notice = noticeRepository.save(newNotice);

        return notice.getNoticeId();
    }

    // 이미지 저장 로직
    private List<NoticeImage> saveImages(List<MultipartFile> imageList) {
        List<NoticeImage> imageEntityList = new ArrayList<>();
        if (imageList != null && !imageList.isEmpty()) {
            for (MultipartFile file : imageList) {
                try {
                    String extension = getFileExtension(file.getContentType());
                    if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png")) {
                        throw new IllegalArgumentException("Invalid file type. Only JPG and PNG are allowed");
                    }
                    String randomName = UUID.randomUUID().toString().replace("-", "");
                    String replaceFileName = randomName + "." + extension;

                    Path uploadPath = Paths.get(IMAGE_DIR);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    try (InputStream inputStream = file.getInputStream()) {
                        Path filePath = uploadPath.resolve(replaceFileName);
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    }
                    imageEntityList.add(NoticeImage.of(file.getOriginalFilename(), replaceFileName, extension));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to save image file");
                }
            }
        }
        return imageEntityList;
    }

    // 파일 확장자 검증 메소드
    private String getFileExtension(String contentType) {
        if (contentType != null) {
            switch (contentType) {
                case "image/jpeg":
                    return "jpg";
                case "image/png":
                    return "png";
                default:
                    throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }
        }
        throw new IllegalArgumentException("Content type cannot be null");
    }

    // 공지사항 수정 메소드
//    public void modifyNotice(final int noticeId, final NoticeRequestDTO noticeRequestDTO) {
//        Notice notice = noticeRepository.findById(noticeId).orElse(null);
//
//        if (notice != null) {
//            notice.updateNotice(noticeRequestDTO.getTitle(), noticeRequestDTO.getContent(),
//                    noticeRequestDTO.getCategory(), noticeRequestDTO.getSubCategory());
//
//            List<NoticeImage> imageEntityList = saveImages(noticeRequestDTO.getImageList());
//            if (imageEntityList != null) {
//                notice.updateImages(imageEntityList);
//            }
//            noticeRepository.save(notice);
//        }
//    }

    // 공지사항 삭제 메소드
    @Transactional
    public void deleteNotice(final int noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);

        if (notice != null) {
            notice.getImages().forEach(image -> deleteImageFile(image.getPath()));
            noticeRepository.delete(notice);
        }
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

    // 이미지 삭제 메소드
    @Transactional
    public void removeImage(int noticeId, int imageId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        if (notice != null) {
            NoticeImage image = notice.getImages().stream().filter(img -> img.getImageId() == imageId).findFirst().orElse(null);
            if (image != null) {
                deleteImageFile(image.getPath());
                notice.getImages().remove(image);
                noticeRepository.save(notice);
            }
        }
    }

    // 이미지 파일 삭제 메소드
    private void deleteImageFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(IMAGE_DIR + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
