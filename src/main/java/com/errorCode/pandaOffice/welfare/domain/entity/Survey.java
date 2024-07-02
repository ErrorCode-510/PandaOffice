package com.errorCode.pandaOffice.welfare.domain.entity;


import com.errorCode.pandaOffice.welfare.dto.request.CreateSurveyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


//설문 entity
//연결관계 어노테이션 설정 필요
@Entity
@Table(name="survey")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문id

    //JoinColumn의 name 속성은 Join 하는 엔티티에서 같은 이름을 가져오는것이 아닌
    // 해당 조인 컬럼의 이름을 설정해주는것이다.
    @ManyToOne
    @JoinColumn(name = "category_id")
    private SurveyCategory category;// 카테고리id

    @Column(name="title",length = 255)
    private String title;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private List<SurveyQuestion> question;

    //단방향 관계(ReplyRecord에는 정의x)
    @OneToMany
    @JoinColumn(name = "survey_id")
    private List<ReplyRecord> replyRecords;


    public Survey(int id, SurveyCategory category, String title, LocalDate startDate, LocalDate endDate, List<SurveyQuestion> question, List<ReplyRecord> replyRecords) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.question = question;
        this.replyRecords = replyRecords;
    }

    public static Survey of(CreateSurveyRequest request,
                            List<SurveyQuestion> question,
                            SurveyCategory category) {
        /* of 메소드 사용법 */
        /* 1. 새로운 엔티티 객체 생성 */
        Survey survey = new Survey();
        /* 2. request 에서 값 가져와서 할당 해주기 */
        survey.title = request.getTitle();
        survey.startDate = request.getStartDate();
        survey.endDate = request.getEndDate();
        survey.category = category;
        /* 필드에 엔티티 설정 */
        survey.question = question;
        /* 3. 설정된 객체 리턴 */
        return survey;
    }


}

