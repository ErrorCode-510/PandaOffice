package com.errorCode.pandaOffice.recruitment.dto.response;

import com.errorCode.pandaOffice.employee.domain.entity.Job;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ScheduleEmployeeResponse {
    private final int id;
    private final String name;
//    private final Job job;
}
