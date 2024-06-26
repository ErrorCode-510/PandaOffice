package com.errorCode.pandaOffice.attendance.dto.OvertimeRecord.response;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OverTimeRecordResponse {

    /* 연장 근무 코드 */
    private int id;

    /* 연장 근무 날짜 */
    private LocalDate date;

    /* 연장 근무 시작일 */
    private LocalTime startTime;

    /* 연장 근무 종료일 */
    private LocalTime endTime;

    /* 연장 근무 타입 */
    private String type;

    /* 주별 누적 초과 근무 시간 */
    private Duration weeklyOverTime;

    /* 달별 누적 초과 근무 시간*/
    private Duration monthlyOverTime;

    public OverTimeRecordResponse(int id, LocalDate date, LocalTime startTime, LocalTime endTime, String type) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }
}
