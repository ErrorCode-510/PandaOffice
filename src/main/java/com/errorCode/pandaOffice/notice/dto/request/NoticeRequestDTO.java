package com.errorCode.pandaOffice.notice.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeRequestDTO {
    
    private String title;  // 게시글 제목
    private String content; // 게시글 내용
    private String category;  // 분류 (전체, 그룹, 경조사)
    private String subCategory;  // 소분류(그륩 : 부서별 / 경조사 : 결혼, 부고, 돌잔치)
    private LocalDate postedDate;  // 작성일
    private int viewCount;  // 조회수
    private char status;  // 공개여부 (Y/N)
    private int employeeId;  // 사원 코드(FK)
    private List<NoticeImageRequestDTO> images = new ArrayList<>();  // 빈 리스트로 초기화

}
