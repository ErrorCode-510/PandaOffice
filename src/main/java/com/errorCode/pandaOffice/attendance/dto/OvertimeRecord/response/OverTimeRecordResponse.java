package com.errorCode.pandaOffice.attendance.dto.OvertimeRecord.response;

import lombok.*;

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

    /* 연장 근무 시작일 */
    private LocalTime startDate;

    /* 연장 근무 종료일 */
    private LocalTime endDate;

    /* 연장 근무 타입 */
    private String type;

}
