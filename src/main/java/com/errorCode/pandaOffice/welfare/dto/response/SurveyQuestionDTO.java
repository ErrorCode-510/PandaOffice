package com.errorCode.pandaOffice.welfare.dto.response;

import com.errorCode.pandaOffice.welfare.domain.entity.SurveyQuestion;

public class SurveyQuestionDTO {
    private int id; // 설문 문항 식별코드
    private int surveyId; // 설문지 코드
    private int questionOrder; //질문순서
    private String question; //질문(문항제목)


    public SurveyQuestionDTO() {}

    public SurveyQuestionDTO(int id, int surveyId, int questionOrder, String question) {
        this.id = id;
        this.surveyId = surveyId;
        this.questionOrder = questionOrder;
        this.question = question;
    }

    public SurveyQuestionDTO(SurveyQuestion surveyQuestion) {
        this.id = surveyQuestion.getId();
        this.questionOrder = surveyQuestion.getQuestionOrder();
        this.question = surveyQuestion.getQuestion();
        this.surveyId = surveyQuestion.getSurvey().getId();
//        this.surveyId = surveyQuestion.getSurvey() != null ? surveyQuestion.getSurvey().getId() : 0; // Survey의 ID를 추가
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "SurveyQuestionDTO{" +
                "id=" + id +
                ", surveyId=" + surveyId +
                ", questionOrder=" + questionOrder +
                ", question='" + question + '\'' +
                '}';
    }
}
