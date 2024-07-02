package com.errorCode.pandaOffice.welfare.domain.entity;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//답변 기록
//설문지 하나당 문항 선택 개수(예를 들면 1번 사원이 1번 설문지에서 매우 그렇다 몇개, 그렇다 몇개 선택했는지 알 수 있음)
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

    @ManyToOne
    @JoinColumn(name="employee_id")//JoinColumn 사용해야하는데 일단 보류
    private Employee employee; //사번(임시작성. 사원 테이블 확정시 이름 변경 )

    @ManyToOne
    @JoinColumn(name="survey_id")
    private Survey survey;

    @Column(name="st_agree")
    private int stAgree; //매우 그렇다 합계 (st -> Strongly '매우')

    @Column(name="agree")
    private int agree; //그렇다 합계

    @Column(name="average")
    private int average; //보통이다 합계

    @Column(name="disagree")
    private int disagree; //약간 아니다 합계

    @Column(name="st_disagree")
    private int stDisagree; // 매우 아니다 합계 (st -> Strongly '매우')

    public ReplyRecord(Employee employee, Survey survey, int stAgree, int agree, int average, int disagree, int stDisagree) {
        this.employee = employee;
        this.survey = survey;
        this.stAgree = stAgree;
        this.agree = agree;
        this.average = average;
        this.disagree = disagree;
        this.stDisagree = stDisagree;
    }

    // 응답 카운트를 업데이트하는 메서드
    public void updateCounts(int stAgree, int agree, int average, int disagree, int stDisagree) {
        this.stAgree += stAgree;
        this.agree += agree;
        this.average += average;
        this.disagree += disagree;
        this.stDisagree += stDisagree;
    }
}
