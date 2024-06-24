package com.errorCode.pandaOffice.welfare.domain.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//답변 기록
//연결관계 어노테이션 설정 필요
@Entity
@Table(name="reply_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyRecord {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="employee_id")//JoinColumn 사용해야하는데 일단 보류
    private int employeeId; //사번(임시작성. 사원 테이블 확정시 이름 변경 )

    @JoinColumn(name="survey_id")
    private int surveyId; //설문코드

    @Column(name="st_agree_count")
    private int stAgreeCount; //매우 그렇다 합계 (st -> Strongly '매우')

    @Column(name="agree_count")
    private int agreeCount; //그렇다 합계

    @Column(name="average_count")
    private int AverageCount; //보통이다 합계

    @Column(name="disagree_count")
    private int DisagreeCount; //약간 아니다 합계

    @Column(name="st_disagree_count")
    private int stDisagreeCount; // 매우 아니다 합계 (st -> Strongly '매우')

    public ReplyRecord(int id, int employeeId, int surveyId, int stAgreeCount, int agreeCount, int averageCount, int disagreeCount, int stDisagreeCount) {
        this.id = id;
        this.employeeId = employeeId;
        this.surveyId = surveyId;
        this.stAgreeCount = stAgreeCount;
        this.agreeCount = agreeCount;
        AverageCount = averageCount;
        DisagreeCount = disagreeCount;
        this.stDisagreeCount = stDisagreeCount;
    }
}
