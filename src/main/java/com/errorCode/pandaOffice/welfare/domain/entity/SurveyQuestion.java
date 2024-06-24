package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


//설문 문항
//연결관계 어노테이션 설정 필요
@Entity
@Table(name="survey_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyQuestion {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문 문항 식별코드

    @Column(name="survey_id") //JoinColumn 사용해야 하지만 일단 보류
    private int surveyId; // 설문지 코드

    @Column(name="question_order")
    private int questionOrder; //질문순서

    @Column(name="question",length = 255)
    private String question; //질문(문항제목)

    public SurveyQuestion(int id, int surveyId, int questionOrder, String question) {
        this.id = id;
        this.surveyId = surveyId;
        this.questionOrder = questionOrder;
        this.question = question;
    }
}
