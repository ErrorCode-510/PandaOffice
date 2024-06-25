package com.errorCode.pandaOffice.notice.dto.request;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.*;

import java.time.LocalDate;

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
    private Employee employeeId;  // 사원 코드
    private LocalDate postedDate;  // 작성일
    private int viewCount;  // 조회수
    private char status;  // 상태
}
