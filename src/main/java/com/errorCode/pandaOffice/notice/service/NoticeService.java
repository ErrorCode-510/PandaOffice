package com.errorCode.pandaOffice.notice.service;
import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.common.util.FileUploadUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentAttachedFile;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeImageRepository;
import com.errorCode.pandaOffice.notice.domain.repository.NoticeRepository;
import com.errorCode.pandaOffice.notice.dto.request.CreateNoticeRequestDTO;
import lombok.RequiredArgsConstructor;
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
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor    // 생성자 생략 가능
public class NoticeService {

    @Value("${image.image-dir}")
    private String uploadDir;

    @Value("${image.image-url}")
    private String uploadUrl;

    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    private final EmployeeRepository employeeRepository;

    @Value("${image.image-dir}")
    private String IMAGE_DIR; // = "src/main/resources/static/uploadFiles"

    /* 공지사항 등록 메소드 */
    public int createNotice(CreateNoticeRequestDTO createNoticeRequestDTO, List<MultipartFile> imageList) {
        /* 현재 로그인한 사원의 사번 토큰에서 가져오기 */
        int employeeId = TokenUtils.getEmployeeId();
        /* 가져온 사번으로 DB 에서 사원 엔티티 가져오기 */
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow();
        /* 사진 관리 */
        /* 파일 저장 로직. 파일을 저장하고 원본명, 경로, 타입을 가진 엔티티 리스트를 반환한다. */
        List<NoticeImage> imageEntityList = new ArrayList<>();
        /* 넘어온 이미지가 있을 경우 */
        if (imageList != null) {
            /* 반복문 실행 */
            imageList.forEach(file -> {
                /* 저장할 경로 생성 로직. 우선 랜덤한 이름 생성 */
                String randomName = UUID.randomUUID().toString().replace("-", "");
                /* saveFile(저장할 경로, 저장할 이름, 저장할 파일)*/
                String path = FileUploadUtils.saveFile(IMAGE_DIR, randomName, file);
                /* 파일의 확장자 가져옴 */
                String extension = file.getContentType();
                /* 저장 확장자가 jpg 나 png 아닐 경우 오류 빌생시키기 */
//                if(!extension.equals("jpg? png?")){
//                    throw new Exception();
//                }
                /* 저장된 파일을 찾을 수 있게 DB 에 파일의 정보 저장 */
                imageEntityList.add(NoticeImage.of(file.getName(), path, extension));
            });
        }
        /* 공부해야될거 of, from 메소드 (생성자 역할)
        * repository 의 find, save 메소드
        * 영속성 전이(CasCade)
        * 사진 저장 과정
        *   1. 사진 리스트를 @RequestPart 로 받아옴
        *   2. 리스트를 정해진 경로에 저장 (yml)
        *   3. 파일에 대한 정보를 저장 (사진을 다시 찾을 수 있는 단서)
        *   4. 저장된 경로 DB 에 저장할 수 있게 entity 로 변환
        *   5. 영속성 전이를 통하여 저장(공지사항 안에 사진 리스트를 넣어버리고 통째로 저장)
        * */
        Notice newNotice = Notice.of(employee, imageEntityList, createNoticeRequestDTO);

        /* noticeRepository.save(저장할객체) -> 객체를 DB에 저장해주는 메소드 */
        noticeRepository.save(newNotice);

        return newNotice.getNoticeId();
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
        return noticeRepository.findById(noticeId).orElse(null);
    }

    // 조회수 증가
    @Transactional
    public void incrementViewCount(int noticeId) {
        if (noticeRepository.existsById(noticeId)) {
            noticeRepository.incrementViewCount(noticeId);
        }
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
                .forEach(noticeRepository::delete);     // 안되면 이 줄 탓일 가능성 큼
    }

//    // 이미지 업로드
//    @Transactional
//    public void uploadImage(MultipartFile file, NoticeImageRequestDTO noticeImageRequestDTO) {
//        Notice notice = noticeRepository.findById(noticeImageRequestDTO.getNoticeId()).orElse(null);
//
//        // 실제 이미지 파일 업로드 로직을 구현
//        if (notice != null) {
//            String absolutePath = Paths.get(uploadDir).toAbsolutePath().toString();
//            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//            String filePath = Paths.get(absolutePath, fileName).toString();
//            String imageUrl = imageUrlBase + fileName;
//        }
//
//
//        try {
//            Files.createDirectories(Paths.get(uploadDir));
//            Path path = Paths.get(filePath);
//            Files.write(path, file.getBytes());
//        } catch (IOException e) {
//            return;
//        }
//
//        NoticeImage image = new NoticeImage(filePath, fileName, getFileExtention(fileName), notice.getNoticeId());
//        notice.addImage(image);
//        noticeRepository.save(notice);
//    }

    private String getFileExtention(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot + 1);
    }

    // 이미지 파일 삭제
    private void deleteImageFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            return;
        }
    }


    // 이미지 삭제
//    @Transactional
//    public void removeImage(int noticeId, int imageId) {
//        Notice notice = noticeRepository.findById(noticeId).orElse(null);
//
//        if (notice != null) {
//            NoticeImage image = notice.getImages().stream()
//                    .filter(img -> img.getImageId() == imageId)
//                    .findFirst()
//                    .orElse(null);
//        }
//
//        if (image != null) {
//            notice.removeImage(image);
//            noticeRepository.save(notice);
//
//            // 실제 파일 시스템에서 이미지 파일 삭제
//            deleteImageFile(image.getPath());
//        }
//    }
}
