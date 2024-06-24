package com.errorCode.pandaOffice.recruitment.dto.response;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.recruitment.domain.entity.InterviewSchedule;
import com.errorCode.pandaOffice.recruitment.domain.entity.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewScheduleResponse {

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

    public static InterviewScheduleResponse from(final InterviewSchedule interviewSchedule) {
        return new InterviewScheduleResponse(
                interviewSchedule.getId(),
                interviewSchedule.getName(),
                interviewSchedule.getStartDate(),
                interviewSchedule.getEndDate(),
                interviewSchedule.getStartTime(),
                interviewSchedule.getPlace(),
                interviewSchedule.getEmployee(),
                interviewSchedule.getEmployee2(),
                interviewSchedule.getEmployee3()
        );
    }
}
