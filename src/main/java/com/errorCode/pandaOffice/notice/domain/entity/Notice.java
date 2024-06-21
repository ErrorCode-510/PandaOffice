package com.errorCode.pandaOffice.notice.domain.entity;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.notice.dto.request.CreateNoticeRequestDTO;
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    /* DB 컬럼에는 employee_id 로 저장됨
    * 자바에서는 자동으로 조인해서 해당되는 사원을 가져옴 */
    private Employee employee;  // 사원 코드(FK)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeImage> images;

    public static Notice of(Employee employee, List<NoticeImage> imageEntityList, CreateNoticeRequestDTO createNoticeRequestDTO) {
        Notice newNotice = new Notice();
        newNotice.title = createNoticeRequestDTO.getTitle();
        newNotice.content = createNoticeRequestDTO.getContent();
        newNotice.category = createNoticeRequestDTO.getCategory();
        newNotice.subCategory = createNoticeRequestDTO.getSubCategory();
        newNotice.postedDate = LocalDate.now();
        newNotice.viewCount = 0;
        newNotice.status = 'Y';
        newNotice.employee = employee;
        newNotice.images = imageEntityList;
        return newNotice;
    }


    //  이미지 추가 메소드
    public void addImage(NoticeImage image) {
        images.add(image);
    }

    // 이미지 삭제 메소드
    public void removeImage(NoticeImage image) {
        images.remove(image);
    }

}
