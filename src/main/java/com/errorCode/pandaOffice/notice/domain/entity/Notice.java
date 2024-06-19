package com.errorCode.pandaOffice.notice.domain.entity;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;  // 사원 코드(FK)

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeImage> images = new ArrayList<>();

    //  이미지 추가 메소드
    public void addImage(NoticeImage image) {
        images.add(image);
        image.setNotice(this);
    }

    // 이미지 삭제 메소드
    public void removeImage(NoticeImage image) {
        images.remove(image);
        image.setNotice(null);
    }

    // 조회수 메소드
    public void setViewCount(int i) {
    }

    // 게시글 수정 작성일 메소드
    public void setPostedDate(LocalDate now) {
    }

    // 게시글 수정 제목 메소드
    public void setTitle(String title) {
    }

    // 게시글 수정 내용 메소드
    public void setContent(String content) {
    }

    // 게시글 수정 분류 메소드
    public void setCategory(String category) {
    }

    // 게시글 수정 소분류 메소드
    public void setSubCategory(String subCategory) {
    }

    // 게시글 수정 공개여부 메소드
    public void setStatus(char status) {
    }
}
