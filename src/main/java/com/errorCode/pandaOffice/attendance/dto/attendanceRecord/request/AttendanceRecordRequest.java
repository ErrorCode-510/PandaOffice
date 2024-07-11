package com.errorCode.pandaOffice.attendance.dto.attendanceRecord.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@AllArgsConstructor
public class AttendanceRecordRequest {

    private LocalDate checkInDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;


}
