package com.errorCode.pandaOffice.recruitment.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "interview_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterviewSchedule {

    /* 면접 일정 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 일정명 */
    @Column(nullable = false)
    private String name;

    /* 메모 */
    @Column
    private String memo;

    /* 일정 시작일 */
    @Column(nullable = false)
    private LocalDate startDate;

    /* 일정 종료일 */
    @Column(nullable = false)
    private LocalDate endDate;

    /* 일정 시작 일시 */
    @Column(nullable = false)
    private LocalTime startTime;

    /* 면접 장소 */
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    /* 면접관들 */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interviewer_id")
    private Employee employee;

    /* 면접관들 2 */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interviewer_id2")
    private Employee employee2;

    /* 면접관들 3 */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "interviewer_id3")
    private Employee employee3;

    /* 면접자들 */
    @OneToMany
    @JoinColumn(name = "interview_schedule_id")
    private List<Applicant> applicantsList;

    public InterviewSchedule(
            String name, String memo, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            Place place, Employee employee, Employee employee2, Employee employee3, List<Applicant> applicantsList
    ) {
        this.name = name;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.place = place;
        this.employee = employee;
        this.employee2 = employee2;
        this.employee3 = employee3;
        this.applicantsList = applicantsList;
    }

    /* 면접일정 등록 */
    public static InterviewSchedule of(
            final String name, final String memo, final LocalDate startDate, final LocalDate endDate, final LocalTime startTime,
            final Place place, final Employee employee, final Employee employee2, final Employee employee3,
            final List<Applicant> applicantsList
    ) {
        return new InterviewSchedule(
                name,
                memo,
                startDate,
                endDate,
                startTime,
                place,
                employee,
                employee2,
                employee3,
                applicantsList
        );
    }

    /* 면접일정 수정 */
    public void modify(
            String name, String memo, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            Place place, Employee employee, Employee employee2, Employee employee3, List<Applicant> applicantsList
    ) {
        this.name = name;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.place = place;
        this.employee = employee;
        this.employee2 = employee2;
        this.employee3 = employee3;
        this.applicantsList = applicantsList;
    }
}
