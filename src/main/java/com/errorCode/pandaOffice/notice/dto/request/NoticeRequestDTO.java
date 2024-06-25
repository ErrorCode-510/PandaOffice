package com.errorCode.pandaOffice.notice.dto.request;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

// 공지사항 요청 DTO 클래스
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDTO {

    private String title;  // 게시글 제목
    private String content; // 게시글 내용
    private String category;  // 분류 (전체, 그룹, 경조사)
    private String subCategory;  // 소분류(그룹 : 부서별 / 경조사 : 결혼, 부고, 돌잔치)
    private List<NoticeImage> imageList;  // 이미지 파일 리스트
    private Employee employeeId;  // 사원 코드
    private LocalDate postedDate;  // 작성일
    private int viewCount;  // 조회수
    private char status;  // 상태
    // 공지사항 이미지 DTO 클래스
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeImageDTO {
        private String path; // 이미지 파일경로
        private String name; // 이미지 파일이름
        private String extension; // 이미지 확장자
    }
}
