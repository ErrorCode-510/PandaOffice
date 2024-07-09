package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.request;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@RequiredArgsConstructor
public class AttendanceRecordRequest {

    private final LocalDate date;

    private final LocalTime time;

}
