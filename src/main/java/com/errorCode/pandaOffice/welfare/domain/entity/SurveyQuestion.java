package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


//설문 문항
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 설문 문항 식별코드
    private int surveyId; // 설문지 코드
    private int questionOrder; //질문순서

    @Column(length = 255)
    private String question; //질문(문항제목)


    public SurveyQuestion(int id, int surveyId, int questionOrder, String question) {
        this.id = id;
        this.surveyId = surveyId;
        this.questionOrder = questionOrder;
        this.question = question;
    }
}
