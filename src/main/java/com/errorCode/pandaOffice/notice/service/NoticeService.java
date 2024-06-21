package com.errorCode.pandaOffice.notice.service;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeImageRepository;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class NoticeService {

    @Value("${image.image-dir}")
    private String uploadDir;

    @Value("${image.image-url}")
    private String uploadUrl;

    private final NoticeRepository noticeRepository;

    private final NoticeImageRepository noticeImageRepository;

    // 생성자 주입을 통해 NoticeRepository 주입받음
    @Autowired
    public NoticeService(NoticeRepository noticeRepository, NoticeImageRepository noticeImageRepository) {
        this.noticeRepository = noticeRepository;
        this.noticeImageRepository = noticeImageRepository;
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
    public Notice getNoticeById(int noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow();
    }

    // 조회수 증가
    @Transactional
    public void incrementViewCount(int noticeId) {
        if (!noticeRepository.existsById(noticeId)) {
            throw new RuntimeException("Notice not found with id" + noticeId);
        }
        noticeRepository.incrementViewCount(noticeId);
    }

    // 공지사항 등록
    public Notice createNotice(NoticeRequestDTO noticeRequestDTO) {

        Notice notice = new Notice (
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                LocalDate.now(),
                0,
                noticeRequestDTO.getStatus(),
                noticeRequestDTO.getEmployeeId(),
                null
        );

        List<NoticeImageRequestDTO> imageDTOs = noticeRequestDTO.getImages();
        if (imageDTOs != null) {
            for (NoticeImageRequestDTO imageDTO : imageDTOs) {
                NoticeImage image = new NoticeImage(imageDTO.getPath(), imageDTO.getName(), imageDTO.getExtention());
                notice.getImages().add(image);
            }
        }
        return noticeRepository.save(notice);
    }

    // 공지사항 수정 (지정된 ID의 공지사항 수정 / 제목, 내용, 분류, 소분류, 공개여부)
    @Transactional
    public Notice updateNotice(int id, NoticeRequestDTO noticeRequestDTO) {

        Notice existingNotice = noticeRepository.findById(id)
                .orElseThrow();


        List<NoticeImage> newImages = noticeRequestDTO.getImages().stream()
                        .map(dto -> new NoticeImage(dto.getPath(), dto.getName(), dto.getExtention()))
                                .collect(Collectors.toList());

        existingNotice.updateNotice(
                noticeRequestDTO.getTitle(),
                noticeRequestDTO.getContent(),
                noticeRequestDTO.getCategory(),
                noticeRequestDTO.getSubCategory(),
                noticeRequestDTO.getStatus(),
                noticeRequestDTO.getEmployeeId(),
                newImages
        );
        return noticeRepository.save(existingNotice);
    }

    // 공지사항 삭제 (지정된 ID의 공지사항 삭제)
    @Transactional
    public void deleteNotice(int noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if (notice.isPresent()) {
            Notice existingNotice = notice.get();
            for (NoticeImage image : existingNotice.getImages()) {
                deleteImageFile(image.getPath());
            }
            noticeRepository.delete(existingNotice);
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

    // 이미지 업로드
    @Transactional
    public void uploadImage(MultipartFile file, NoticeImageRequestDTO noticeImageRequestDTO) {
        Notice notice = noticeRepository.findById(noticeImageRequestDTO.getNoticeId())
                .orElseThrow();

        // 실제 이미지 파일 업로드 로직을 구현
        String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = Paths.get(absolutePath, fileName).toString();


        try {
            Files.createDirectories(Paths.get(uploadDir));
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        NoticeImage image = new NoticeImage(filePath, fileName, getFileExtention(fileName));
        notice.getImages().add(image);
        noticeRepository.save(notice);
    }

    private String getFileExtention(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot + 1);
    }

    // 이미지 파일 삭제
    private void deleteImageFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Filed to delete image file", e);
        }
    }

    // 이미지 삭제
    @Transactional
    public void removeImage(int noticeId, int imageId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow();

        NoticeImage image = notice.getImages().stream()
                .filter(img -> img.getImageId() == imageId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found with id" + imageId));

        notice.getImages().remove(image);
        noticeRepository.save(notice);

        // 실제 파일 시스템에서 이미지 파일 삭제
        deleteImageFile(image.getPath());
    }
}
