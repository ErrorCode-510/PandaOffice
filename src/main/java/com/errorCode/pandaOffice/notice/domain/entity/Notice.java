package com.errorCode.pandaOffice.notice.domain.entity;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;


// 공지사항(게시글)
@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 게시글 코드(PK)

    @Column(name = "title")
    private String title;  // 게시글 제목

    @Column(name = "content")
    private String content;  // 게시글 내용

    @Column(name = "category")
    private String category;  // 분류 (전체, 그룹, 경조사)

    @Column(name = "sub_category")
    private String subCategory;  // 소분류 (그룹 : 부서별 / 경조사 : 결혼, 부고, 돌찬치)

    @Column(name = "posted_date")
    private LocalDate postedDate;  // 작성일

    @Column(name = "view_count")
    private int viewCount;  // 조회수

    @Column(name = "status")
    private char status;  // 공개여부 (Y/N)

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;  // 사원 코드(FK)
}
