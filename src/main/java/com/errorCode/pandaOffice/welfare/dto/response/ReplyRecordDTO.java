package com.errorCode.pandaOffice.welfare.dto.response;

public class ReplyRecordDTO {
    private int id;
    private int employeeId; //사번(임시작성. 사원 테이블 확정시 이름 변경 )
    private int surveyId; //설문코드
    private int stAgree; //매우 그렇다 합계 (st -> Strongly '매우')
    private int agree; //그렇다 합계
    private int average; //보통이다 합계
    private int disagree; //약간 아니다 합계
    private int stDisagree; // 매우 아니다 합계 (st -> Strongly '매우')

    public ReplyRecordDTO(){

    }

    public ReplyRecordDTO(int id, int employeeId, int surveyId, int stAgree, int agree, int average, int disagree, int stDisagree) {
        this.id = id;
        this.employeeId = employeeId;
        this.surveyId = surveyId;
        this.stAgree = stAgree;
        this.agree = agree;
        this.average = average;
        this.disagree = disagree;
        this.stDisagree = stDisagree;
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

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getStAgree() {
        return stAgree;
    }

    public void setStAgree(int stAgree) {
        this.stAgree = stAgree;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public int getDisagree() {
        return disagree;
    }

    public void setDisagree(int disagree) {
        this.disagree = disagree;
    }

    public int getStDisagree() {
        return stDisagree;
    }

    public void setStDisagree(int stDisagree) {
        this.stDisagree = stDisagree;
    }
}
