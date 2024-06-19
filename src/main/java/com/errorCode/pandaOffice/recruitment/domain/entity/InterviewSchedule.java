package com.errorCode.pandaOffice.recruitment.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


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

    /* 면접관 1 */
    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private Employee employee1;

    /* 면접관 1 */
    @ManyToOne
    @JoinColumn(name = "interviewer_id2")
    private Employee employee2;

    /* 면접관 1 */
    @ManyToOne
    @JoinColumn(name = "interviewer_id3")
    private Employee employee3;
}
