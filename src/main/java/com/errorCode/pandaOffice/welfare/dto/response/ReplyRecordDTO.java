package com.errorCode.pandaOffice.welfare.dto.response;

import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;

public class ReplyRecordDTO {
    private int id;
    private int employeeId; //사번(임시작성. 사원 테이블 확정시 이름 변경 )
    private int jobId; //직급 코드
    private int questionId;//질문 id
    private int surveyId; //설문코드
    private int answer; //질문 기록

    public ReplyRecordDTO(){

    }

    public ReplyRecordDTO(ReplyRecord replyRecord) {
        this.id = replyRecord.getId();
        this.employeeId = replyRecord.getEmployee().getEmployeeId();
        this.jobId = replyRecord.getJob().getId();
        this.surveyId = replyRecord.getSurvey().getId();
        this.questionId = replyRecord.getQuestion().getId();
        this.answer = replyRecord.getAnswer();
    }

    public ReplyRecordDTO(int id, int employeeId, int jobId, int questionId, int surveyId, int answer) {
        this.id = id;
        this.employeeId = employeeId;
        this.jobId = jobId;
        this.questionId = questionId;
        this.surveyId = surveyId;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
