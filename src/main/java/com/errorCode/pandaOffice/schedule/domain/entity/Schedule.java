package com.errorCode.pandaOffice.schedule.domain.entity;

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
    @Column(nullable = true)
    private LocalDate startDate;

    /* 일정 종료일 */
    @Column(nullable = true)
    private LocalDate endDate;

    /* 일정 시작 일시 */
    @Column(nullable = true)
    private LocalTime  startTime;

}
