package com.errorCode.pandaOffice.schedule.dto.request;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ScheduleModifyRequest {

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
}
