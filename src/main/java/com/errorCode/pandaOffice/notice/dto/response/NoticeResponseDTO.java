package com.errorCode.pandaOffice.notice.dto.response;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import com.errorCode.pandaOffice.notice.domain.entity.NoticeImage;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// 공지사항 응답 DTO 클래스
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDTO {

    private int noticeId;  // 게시글 코드(PK)
    private String title;  // 게시글 제목
    private String content;  // 게시글 내용
    private String category;  // 분류 (전체, 그룹, 경조사)
    private String subCategory;  // 소분류 (그룹 : 부서별 / 경조사 : 결혼, 부고, 돌잔치)
    private LocalDate postedDate;  // 작성일
    private int viewCount;  // 조회수
    private char status;  // 공개여부 (Y/N)
    private int employeeId;  // 사원 코드(FK)
    private String name;  // 사원 이름
    private String job;  // 사원 직급
    private List<NoticeImageDTO> images;  // 이미지 리스트

    // Notice 객체를 NoticeResponseDTO로 변환하는 메소드
    public static NoticeResponseDTO from(Notice notice, String uploadUrl) {
        List<NoticeImageDTO> imageDTOs = notice.getImages().stream()
                .map(image -> NoticeImageDTO.from(image, uploadUrl))
                .collect(Collectors.toList());

        return new NoticeResponseDTO(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getCategory(),
                notice.getSubCategory(),
                notice.getPostedDate(),
                notice.getViewCount(),
                notice.getStatus(),
                notice.getEmployee().getEmployeeId(),
                notice.getEmployee().getName(),
                notice.getEmployee().getJob().getTitle(),
                imageDTOs
        );
    }

    // 공지사항 이미지 응답 DTO 클래스
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeImageDTO {

        private int imageId;  // 이미지 코드(PK)
        private String path;  // 이미지 파일경로
        private String name;  // 이미지 파일이름
        private String extension;  // 이미지 확장자
        private String uploadUrl;  // 추가된 메타데이터 필드

        // NoticeImage 객체를 NoticeImageDTO로 변환하는 메소드
        public static NoticeImageDTO from(NoticeImage image, String uploadUrl) {
            return new NoticeImageDTO (
                    image.getImageId(),
                    image.getPath(),
                    image.getName(),
                    image.getExtension(),
                    uploadUrl + "/" + image.getPath()
            );
        }
    }
}
