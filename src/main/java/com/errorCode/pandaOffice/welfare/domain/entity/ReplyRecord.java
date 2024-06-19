package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//답변 기록
//연결관계 어노테이션 설정 필요
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int employeeId; //사번(임시작성. 사원 테이블 확정시 이름 변경 )

    private int surveyId; //설문코드

    private int stAgreeCount; //매우 그렇다 합계 (st -> Strongly '매우')
    private int agreeCount; //그렇다 합계
    private int Average; //보통이다 합계
    private int Disagree; //약간 아니다 합계
    private int stDisagreeCount; // 매우 아니다 합계 (st -> Strongly '매우')


    public ReplyRecord(int employeeId, int surveyId, int stAgreeCount, int agreeCount, int Average, int disagree, int stDisagreeCount) {
        this.employeeId = employeeId;
        this.surveyId = surveyId;
        this.stAgreeCount = stAgreeCount;
        this.agreeCount = agreeCount;
        this.Average = Average;
        this.Disagree = disagree;
        this.stDisagreeCount = stDisagreeCount;
    }
}
