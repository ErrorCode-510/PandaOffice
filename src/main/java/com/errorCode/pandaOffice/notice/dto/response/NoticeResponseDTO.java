package com.errorCode.pandaOffice.notice.dto.response;

import com.errorCode.pandaOffice.notice.domain.entity.Notice;
import lombok.*;

import java.time.LocalDate;

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

    // Notice 객체를 NoticeResponseDTO로 변환하는 메소드
    public static NoticeResponseDTO from(Notice notice) {

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
                notice.getEmployee().getJob().getTitle()
        );
    }
}
