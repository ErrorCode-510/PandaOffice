package com.errorCode.pandaOffice.welfare.domain.entity;


import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private int answer;

    // Constructor
    public ReplyRecord(Employee employee, Job job, Survey survey, SurveyQuestion question, int answer) {
        this.employee = employee;
        this.job = job;
        this.survey = survey;
        this.question = question;
        this.answer = answer;
    }

    // 응답 업데이트 메서드
    public void updateAnswer(int answer) {
        this.answer = answer;
    }

}
