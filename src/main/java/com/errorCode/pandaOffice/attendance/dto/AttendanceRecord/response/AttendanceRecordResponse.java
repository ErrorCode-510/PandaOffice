package com.errorCode.pandaOffice.attendance.dto.AttendanceRecord.response;

import lombok.*;

import java.time.Duration;
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

    /* 주별 누적 시간 */
    private Duration weeklyCumulativeTime;

    /* 주별 잔여 시간 */
    private Duration remainingTime;

    /* 달별 누적 시간 */
    private Duration monthlyCumulativeTimes;

    public AttendanceRecordResponse(int id, LocalDate date, LocalTime checkInTime, LocalTime checkOutTime, LocalTime totalLateTime) {
        this.id = id;
        this.date = date;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalLateTime = totalLateTime;
    }
}
