package com.errorCode.pandaOffice.recruitment.dto.request;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class InterviewScheduleCreateRequest {

    /* 면접 일정 코드 */
    private final int id;

    /* 일정명 */
    private final String name;

    /* 일정 시작일 */
    private final LocalDate startDate;

    /* 일정 종료일 */
    private final LocalDate endDate;

    /* 일정 시작 일시 */
    private final LocalTime startTime;

    /* 면접 장소 */
    private final Place place;

    /* 면접관 1 */
    private final Employee employee;

    /* 면접관 2 */
    private final Employee employee2;

    /* 면접관 3 */
    private final Employee employee3;
}
