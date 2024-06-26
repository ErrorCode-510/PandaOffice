package com.errorCode.pandaOffice.schedule.domain.entity;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    /* 일정 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /* 일정명 */
    @Column(nullable = false)
    private String name;

    /* 내용 */
    @Column(nullable = false)
    private String description;

    /* 일정 시작일 */
    @Column
    private LocalDate startDate;

    /* 일정 종료일 */
    @Column
    private LocalDate endDate;

    /* 일정 시작 일시 */
    @Column
    private LocalTime  startTime;

    /* 사원 */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Schedule(
            String name, String description, LocalDate startDate, LocalDate endDate,
            LocalTime startTime, Employee employee
    ) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.employee = employee;
    }

    /* 캘린더 일정 등록 생성자 */
    public static Schedule of(
            String name, String description, LocalDate startDate, LocalDate endDate,
            LocalTime startTime, Employee employee
    ) {
        return new Schedule(
                name,
                description,
                startDate,
                endDate,
                startTime,
                employee
        );
    }

    /* 캘린더 일정 수정 */
    public void modify (Schedule schedule) {
        this.name = schedule.getName();
        this.description = schedule.getDescription();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.startTime = schedule.getStartTime();
        this.employee = schedule.getEmployee();
    }

}
