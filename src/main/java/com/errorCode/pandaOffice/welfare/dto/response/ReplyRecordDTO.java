package com.errorCode.pandaOffice.welfare.dto.response;

public class ReplyRecordDTO {
    private int id;
    private int employeeId; //사번(임시작성. 사원 테이블 확정시 이름 변경 )
    private int surveyId; //설문코드
    private int stAgreeCount; //매우 그렇다 합계 (st -> Strongly '매우')
    private int agreeCount; //그렇다 합계
    private int Average; //보통이다 합계
    private int Disagree; //약간 아니다 합계
    private int stDisagreeCount; // 매우 아니다 합계 (st -> Strongly '매우')

    public ReplyRecordDTO(){

    }
    public ReplyRecordDTO(int id, int employeeId, int surveyId, int stAgreeCount, int agreeCount, int average, int disagree, int stDisagreeCount) {
        this.id = id;
        this.employeeId = employeeId;
        this.surveyId = surveyId;
        this.stAgreeCount = stAgreeCount;
        this.agreeCount = agreeCount;
        Average = average;
        Disagree = disagree;
        this.stDisagreeCount = stDisagreeCount;
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

    public int getStAgreeCount() {
        return stAgreeCount;
    }

    public void setStAgreeCount(int stAgreeCount) {
        this.stAgreeCount = stAgreeCount;
    }

    public int getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    public int getAverage() {
        return Average;
    }

    public void setAverage(int average) {
        Average = average;
    }

    public int getDisagree() {
        return Disagree;
    }

    public void setDisagree(int disagree) {
        Disagree = disagree;
    }

    public int getStDisagreeCount() {
        return stDisagreeCount;
    }

    public void setStDisagreeCount(int stDisagreeCount) {
        this.stDisagreeCount = stDisagreeCount;
    }

    @Override
    public String toString() {
        return "ReplyRecordDTO{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", surveyId=" + surveyId +
                ", stAgreeCount=" + stAgreeCount +
                ", agreeCount=" + agreeCount +
                ", Average=" + Average +
                ", Disagree=" + Disagree +
                ", stDisagreeCount=" + stDisagreeCount +
                '}';
    }
}
