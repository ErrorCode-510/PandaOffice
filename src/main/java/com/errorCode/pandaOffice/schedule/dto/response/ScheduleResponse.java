package com.errorCode.pandaOffice.schedule.dto.response;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.schedule.domain.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ScheduleResponse {

    /* 일정명 */
    private final String name;

    /* 내용 */
    private final String description;

    /* 일정 시작일 */
    private final LocalDate startDate;

    /* 일정 종료일 */
    private final LocalDate endDate;

    /* 일정 시작 일시 */
    private final LocalTime startTime;

    /* 사원 */
    private final Employee employee;

    /* 엔티티 -> DTO 변환 */
    public static ScheduleResponse from(final Schedule schedule) {
        return new ScheduleResponse(
                schedule.getName(),
                schedule.getDescription(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getStartTime(),
                schedule.getEmployee()
        );
    }
}
