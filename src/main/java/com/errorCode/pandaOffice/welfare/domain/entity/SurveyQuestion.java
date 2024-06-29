package com.errorCode.pandaOffice.welfare.domain.entity;


import com.errorCode.pandaOffice.welfare.dto.request.CreateSurveyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


//설문 문항
//연결관계 어노테이션 설정 필요
@Entity
@Table(name="survey_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class SurveyQuestion {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문 문항 식별코드

    @Column(name="question_order")
    private int questionOrder; //질문순서


    @Column(name="question",length = 255)
    private String question; //질문(문항제목)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Transient
    private EntityManager entityManager;

    public SurveyQuestion(int id, int questionOrder, String question, Survey survey) {
        this.id = id;
        this.questionOrder = questionOrder;
        this.question = question;
        this.survey = survey;
    }


    public static SurveyQuestion of(CreateSurveyRequest.Question que) {
        SurveyQuestion question = new SurveyQuestion();
        question.questionOrder = que.getOrder();
        question.question = que.getQuestion();
        return question;
    }

    // 질문과 연관된 설문을 업데이트하는 메서드
    public void updateWithSurvey(int questionOrder, String question, Survey survey) {
        this.questionOrder = questionOrder;
        this.question = question;
        this.survey = survey;
    }

    // 질문을 업데이트하는 메서드
    public SurveyQuestion update(int questionOrder, String question) {
        this.questionOrder = questionOrder;
        this.question = question;
        return this;
    }

    // surveyQuestion의 id값과 question의 id값을 일치시키기위한 메서드
    public void updateQuestionOrder() {
        this.questionOrder = this.id;
    }


}

