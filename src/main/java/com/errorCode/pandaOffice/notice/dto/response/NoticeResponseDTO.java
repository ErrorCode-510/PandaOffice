package com.errorCode.pandaOffice.notice.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<NoticeImageResponseDTO> images;  // 이미지 리스트

}
