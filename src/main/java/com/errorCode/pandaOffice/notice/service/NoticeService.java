package com.errorCode.pandaOffice.notice.service;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import com.errorCode.pandaOffice.notice.dto.request.NoticeImageRequestDTO;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
public class NoticeService {
    @Value("${image.image-dir}")
    private String uploadDir;

    @Value("${image.image-url}")
    private String uploadUrl;

    private final NoticeRepository noticeRepository;
    private final EmployeeRepository employeeRepository;

    // 생성자 주입을 통해 NoticeRepository 주입받음
    @Autowired
    public NoticeService(NoticeRepository noticeRepository, EmployeeRepository employeeRepository) {
        this.noticeRepository = noticeRepository;
        this.employeeRepository = employeeRepository;
    }

    // 전체 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    public Page<Notice> getAllNotices(Pageable pageable) {
        // 페이징 요청 객체를 사용하여 모든 공지사항을 조회해서 반환
        return noticeRepository.findAll(pageable);
    }

    // 분류와 소분류별 공지사항 조회 (페이징 및 정렬) (최신순으로 조회)
    public Page<Notice> getNoticesByCategory(String category, String subCategory, Pageable pageable) {
        // category 와 subCategory 에 해당하는 공지사항을 페이징 요청 객체를 사용하여 조회해서 반환
        return noticeRepository.findByCategoryAndSubCategory(category, subCategory, pageable);
    }

    // 특정 공지사항 조회
    // 지정된 ID의 공지사항을 조회
    public Notice getNoticeById(int noticeId) {
        // 공지사항이 존재하지 않으면 NotFoundException 예외를 발생시킴
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + noticeId));
    }

    // 조회수 증가
    @Transactional
    public void incrementViewCount(int noticeId) {
        // 지정된 ID의 공지사항을 조회, 존재하지 않으면 예외 발생
        if (!noticeRepository.existsById(noticeId)) {
            throw new RuntimeException("Notice not found with id" + noticeId);
        }
        // 조회수 증가
        noticeRepository.incrementViewCount(noticeId);
    }

    // 공지사항 등록
    // 새로운 공지사항 등록, 등록 시 생성일자와 초기 조회수 설정
    public Notice createNotice(NoticeRequestDTO noticeRequestDTO) {
        Employee employee = employeeRepository.findById(noticeRequestDTO.getEmployeeId())
                        .orElseThrow(() -> new RuntimeException("Employee not found with id" + noticeRequestDTO.getEmployeeId()));


        Notice notice = new Notice (
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                LocalDate.now(),
                0,
                noticeRequestDTO.getStatus(),
                employee,
                null
        );

        List<NoticeImageRequestDTO> imageDTOs = noticeRequestDTO.getImages();
        if (imageDTOs != null) {
            for (NoticeImageRequestDTO imageDTO : imageDTOs) {
                NoticeImage image = new NoticeImage(imageDTO.getPath(), imageDTO.getName(), imageDTO.getExtention(), notice);
                notice.addImage(image);  // addImage 메소드 호출
            }
        }

        return noticeRepository.save(notice);
    }

    // 공지사항 수정 (지정된 ID의 공지사항 수정 / 제목, 내용, 분류, 소분류, 공개여부)
    @Transactional
    public Notice updateNotice(int id, NoticeRequestDTO noticeRequestDTO) {

        LOGGER.info("updating notice with id" + id);

        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + id));

        Employee employee = employeeRepository.findById(noticeRequestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id" + noticeRequestDTO.getEmployeeId()));

        List<NoticeImage> newImages = noticeRequestDTO.getImages() != null ?
                noticeRequestDTO.getImages().stream()
                        .map(dto -> new NoticeImage(dto.getPath(), dto.getName(), dto.getExtention(), existingNotice))
                        .collect(Collectors.toList()) : null;

        existingNotice.updateNotice(
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                noticeRequestDTO.getStatus(),
                employee,
                newImages
        );
        return noticeRepository.save(existingNotice);
    }

    // 공지사항 삭제 (지정된 ID의 공지사항 삭제)
    @Transactional
    public void deleteNotice(int id) {
        Notice notice = noticeRepository.findById(id)
                        .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + id));
        noticeRepository.delete(notice);
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

    // 이미지 업로드
    @Transactional
    public void uploadImage(MultipartFile file, NoticeImageRequestDTO noticeImageRequestDTO) {
        Notice notice = noticeRepository.findById(noticeImageRequestDTO.getNoticeId())
                .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + noticeImageRequestDTO.getNoticeId()));

        // 실제 이미지 파일 업로드 로직을 구현
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = Paths.get(absolutePath, fileName).toString();
        String imageUrl = uploadUrl + fileName;  // URL 설정

        try {
            Files.createDirectories(Paths.get(uploadDir));
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        NoticeImage image = new NoticeImage(filePath, fileName, getFileExtention(fileName), notice);
        notice.addImage(image);  //  addImage 메소드 사용
        noticeRepository.save(notice);

//        return imageUrl;
    }

    private String getFileExtention(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot + 1);
    }

    // 이미지 삭제
    @Transactional
    public void removeImage(int noticeId, int imageId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new com.errorCode.pandaOffice.common.exception.type.NotFoundException("Notice not found with id" + noticeId));

        NoticeImage image = notice.getImages().stream()
                .filter(img -> img.getImageId() == imageId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found with id" + imageId));

        notice.removeImage(image);  // removeImage 메소드 사용
        noticeRepository.save(notice);

        // 실제 파일 시스템에서 이미지 파일 삭제
        try {
            Files.deleteIfExists(Paths.get(image.getPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }
    }
}
