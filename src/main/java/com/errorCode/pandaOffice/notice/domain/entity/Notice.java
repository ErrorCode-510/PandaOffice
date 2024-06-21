package com.errorCode.pandaOffice.notice.domain.entity;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// 공지사항(게시글)
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

    @Column(name = "employee_id", nullable = false)
    private int employeeId;  // 사원 코드(FK)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeImage> images = new ArrayList<>();

    public Notice(String title, String content, String category, String subCategory, LocalDate postedDate, int viewCount, char status, int employeeId, List<NoticeImage> images) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.postedDate = postedDate;
        this.viewCount = viewCount;
        this.status = status;
        this.employeeId = employeeId;

        if (images != null) {
            this.images.addAll(images);
        }
    }

    //  이미지 추가 메소드
    public void addImage(NoticeImage image) {
        images.add(image);
    }

    // 이미지 삭제 메소드
    public void removeImage(NoticeImage image) {
        images.remove(image);
    }

    public void updateNotice(String title, String content, String category, String subCategory, char status, int employeeId, List<NoticeImage> newImages) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.subCategory = subCategory;
        this.status = status;
        this.employeeId = employeeId;

        this.images.clear();
        if (newImages != null) {
            this.images.addAll(newImages);
        }
    }
}
