package com.errorCode.pandaOffice.attendance.dto.AttendanceRecord.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordResponse {
    /* 근무 기록 코드 */
    private int id;

    /* 근무 기록 날짜 */
    private LocalDate date;

    /* 근무 출근 시간 */
    private LocalTime checkInTime;

    /* 근무 퇴근 시간 */
    private LocalTime checkOutTime;

    /* 근무 누적 지각 시간 */
    private LocalTime totalLateTime;

}
