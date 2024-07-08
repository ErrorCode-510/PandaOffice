package com.errorCode.pandaOffice.recruitment.dto.response;

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
}
