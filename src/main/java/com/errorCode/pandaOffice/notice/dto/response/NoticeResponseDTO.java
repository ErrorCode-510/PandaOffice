package com.errorCode.pandaOffice.notice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class NoticeResponseDTO {

    private int id;  // 게시글 코드(PK)
    private String title;  // 게시글 제목
    private String content;  // 게시글 내용
    private String category;  // 분류 (전체, 그룹, 경조사)
    private String subCategory;  // 소분류 (그룹 : 부서별 / 경조사 : 결혼, 부고, 돌잔치)
    private LocalDate postedDate;  // 작성일
    private int viewCount;  // 조회수
    private char status;  // 공개여부 (Y/N)
    private int employeeId;  // 사원 코드(FK)
    private List<NoticeImageResponseDTO> images;  // 이미지 리스트

    public NoticeResponseDTO(int id, String title, String content, String category, String subCategory, LocalDate postedDate, int viewCount, char status, int employeeId, List<NoticeImageResponseDTO> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.postedDate = postedDate;
        this.viewCount = viewCount;
        this.status = status;
        this.employeeId = employeeId;
        this.images = images;
    }

    @Override
    public String toString() {
        return "NoticeResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", postedDate=" + postedDate +
                ", viewCount=" + viewCount +
                ", status=" + status +
                ", employeeId=" + employeeId +
                ", images=" + images +
                '}';
    }
}
