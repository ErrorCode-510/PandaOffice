package com.errorCode.pandaOffice.notice.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.notice.dto.request.NoticeRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 공지사항(게시글) 엔티티 클래스
@Entity
@Table(name = "notice")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;  // 게시글 코드(PK)

    @Column(name = "title", nullable = false)
    private String title;  // 게시글 제목

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 내용

    @Column(name = "category", nullable = false)
    private String category;  // 분류 (전체, 그룹, 경조사)

    @Column(name = "sub_category")
    private String subCategory;  // 소분류 (그룹 : 부서별 / 경조사 : 결혼, 부고, 돌찬치)

    @Column(name = "posted_date", nullable = false)
    private LocalDate postedDate;  // 작성일

    @Column(name = "view_count", nullable = false)
    private int viewCount;  // 조회수

    @Column(name = "status", nullable = false, length = 1)
    private char status;  // 공개여부 (Y/N)

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;  // 사원 코드(FK)

    public Notice(
            String title, String content, String category,
            String subCategory, LocalDate postedDate, int viewCount, char status,
            Employee employee
    ) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.postedDate = postedDate;
        this.viewCount = viewCount;
        this.status = status;
        this.employee = employee;
    }

    public static Notice of (
            final String title, final String content, final String category, final String subCategory, final LocalDate postedDate,
            final int viewCount, final char status, final Employee employee
    ) {
        return new Notice (
                title,
                content,
                category,
                subCategory,
                postedDate,
                viewCount,
                status,
                employee
        );
    }

    // 공지사항 업데이트 메소드
    public void updateNotice(String title, String content, String category, String subCategory, LocalDate postedDate, int viewCount, char status, Employee employee) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.postedDate = postedDate;
        this.viewCount = viewCount;
        this.status = status;
        this.employee = employee;
    }

}
